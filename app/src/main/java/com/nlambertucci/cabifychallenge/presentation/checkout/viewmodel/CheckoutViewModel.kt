package com.nlambertucci.cabifychallenge.presentation.checkout.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nlambertucci.cabifychallenge.common.BaseResponse
import com.nlambertucci.cabifychallenge.domain.model.CheckoutProductItem
import com.nlambertucci.cabifychallenge.domain.usecase.GetShoppingListForCheckoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val getShoppingListForCheckoutUseCase: GetShoppingListForCheckoutUseCase
) : ViewModel() {

    sealed class CheckoutStatus {
        object Loading : CheckoutStatus()
        data class Success(
            val products: List<CheckoutProductItem>,
            val total: Double
        ) : CheckoutStatus()

        data class Error(
            val errorMessage: String?
        ) : CheckoutStatus()
    }

    private val checkoutStatus = MutableLiveData<CheckoutStatus>()
    val checkoutLiveStatus: LiveData<CheckoutStatus> = checkoutStatus
    private var shoppingList: List<CheckoutProductItem> = listOf()
    private var totalAmount: Double = 0.0

    fun getShoppingList() {
        getShoppingListForCheckoutUseCase().onEach { result ->
            when (result) {
                is BaseResponse.Loading -> checkoutStatus.value = CheckoutStatus.Loading
                is BaseResponse.Success -> {
                    shoppingList = result.data ?: throw Exception("Product list must not be null")
                    shoppingList.forEach { item ->
                        totalAmount += item.finalPrice
                    }
                    checkoutStatus.value = CheckoutStatus.Success(shoppingList, totalAmount)
                }
                is BaseResponse.Error -> {
                    checkoutStatus.value = CheckoutStatus.Error(result.message)
                }
            }
        }.launchIn(viewModelScope)
    }
}