package com.nlambertucci.cabifychallenge.presentation.products.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nlambertucci.cabifychallenge.R
import com.nlambertucci.cabifychallenge.common.BaseResponse
import com.nlambertucci.cabifychallenge.data.remote.dto.toProduct
import com.nlambertucci.cabifychallenge.domain.model.Product
import com.nlambertucci.cabifychallenge.domain.usecase.GetDiscountsForProductUseCase
import com.nlambertucci.cabifychallenge.domain.usecase.GetImageForProductUseCase
import com.nlambertucci.cabifychallenge.domain.usecase.GetProductsUseCase
import com.nlambertucci.cabifychallenge.domain.usecase.SaveProductsForCheckoutUseCase
import com.nlambertucci.cabifychallenge.presentation.checkout.CheckoutActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getDiscountsForProductUseCase: GetDiscountsForProductUseCase,
    private val getImageForProductUseCase: GetImageForProductUseCase,
    private val saveProductsForCheckoutUseCase: SaveProductsForCheckoutUseCase
) : ViewModel() {

    sealed class Status {
        object Loading : Status()
        data class Error(
            val errorMessage: String?
        ) : Status()

        data class Success(
            val products: List<Product>
        ) : Status()

        data class Redirect(
            val intent: Intent
        ) : Status()
    }

    private val status = MutableLiveData<Status>()
    val liveStatus: LiveData<Status> = status
    private val shoppingList = mutableListOf<String>()

    fun fetchProducts() {
        getProducts()
    }

    private fun getProducts() {
        getProductsUseCase().onEach { result ->
            when (result) {
                is BaseResponse.Loading -> status.value = Status.Loading
                is BaseResponse.Success -> {
                    result.data ?: return@onEach
                    val productList = result.data.map {
                        it.toProduct(
                            image = getImageForProductUseCase.invoke(it.code),
                            discount = getDiscountsForProductUseCase.invoke(it.code)
                        )
                    }
                    status.value = Status.Success(products = productList)
                }
                is BaseResponse.Error -> status.value = Status.Error(result.message)
            }
        }.launchIn(viewModelScope)
    }

    fun addItemToCart(product: Product) {
        shoppingList.add(product.code)
    }

    fun removeItemFromCart(product: Product) {
        shoppingList.remove(product.code)
    }

    fun initCheckoutFlow(context: Context) {
        status.value = Status.Loading
        try {
            saveProductsForCheckoutUseCase.invoke(shoppingList)
            status.value = Status.Redirect(CheckoutActivity.newInstance(context))
        } catch (e: Exception) {
            status.value = Status.Error(context.getString(R.string.PaymentErrorMessage))
        }
    }

    fun tearDown() {
        status.value = Status.Loading
        shoppingList.clear()
    }

}