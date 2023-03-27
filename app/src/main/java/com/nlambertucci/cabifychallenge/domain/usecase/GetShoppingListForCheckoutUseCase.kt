package com.nlambertucci.cabifychallenge.domain.usecase


import com.nlambertucci.cabifychallenge.common.BaseResponse
import com.nlambertucci.cabifychallenge.common.Constants
import com.nlambertucci.cabifychallenge.data.remote.dto.toProduct
import com.nlambertucci.cabifychallenge.domain.model.CheckoutProductItem
import com.nlambertucci.cabifychallenge.domain.model.Discount
import com.nlambertucci.cabifychallenge.domain.repository.CheckoutRepository
import com.nlambertucci.cabifychallenge.domain.repository.DiscountRepository
import com.nlambertucci.cabifychallenge.domain.repository.ImageRepository
import com.nlambertucci.cabifychallenge.domain.repository.ProductRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetShoppingListForCheckoutUseCase @Inject constructor(
    private val checkoutRepository: CheckoutRepository,
    private val productRepository: ProductRepository,
    private val imageRepository: ImageRepository,
    private val discountRepository: DiscountRepository
) {

    operator fun invoke() = flow {
        val checkoutItems = mutableListOf<CheckoutProductItem>()
        emit(BaseResponse.Loading<List<CheckoutProductItem>>())
        val checkoutProducts = checkoutRepository.getProducts()
        runCatching {
            productRepository.getProducts().products.map { product ->
                product.toProduct(
                    imageRepository.getImageForProduct(product.code),
                    discountRepository.getDiscountForProductId(product.code)
                )
            }
        }.onSuccess {
            checkoutProducts.map { (code, quantity) ->
                val product = it.find { it.code == code }
                product?.let {
                    checkoutItems.add(
                        CheckoutProductItem(
                            product = it,
                            quantity = quantity,
                            finalPrice = getPriceWithDiscount(it.price, quantity, it.discount),
                            totalPrice = it.price * quantity,
                            currencySymbol = Constants.CURRENCY_SYMBOL
                        )
                    )
                }
            }
            emit(BaseResponse.Success<List<CheckoutProductItem>>(checkoutItems))
        }.onFailure {
            emit(BaseResponse.Error<List<CheckoutProductItem>>("Ocurrio un problema al parsear los productos."))
        }
    }

    private fun getPriceWithDiscount(price: Double, quantity: Int, discount: Discount?): Double {
        return when (discount) {
            is Discount.GetDiscountByPercentage -> {
                if (quantity >= discount.products) {
                    price * quantity * (1 - discount.percentage)
                } else {
                    price * quantity
                }
            }

            is Discount.Get2x1Discount -> {
                if (discount.type == "pairDiscount" && quantity % 2 == 0) {
                    price * (quantity / 2)
                } else {
                    price * quantity
                }
            }
            else -> price * quantity
        }
    }
}
