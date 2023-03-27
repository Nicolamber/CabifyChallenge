package com.nlambertucci.cabifychallenge.domain.usecase

import com.nlambertucci.cabifychallenge.domain.model.PreOrderProducts
import com.nlambertucci.cabifychallenge.domain.repository.CheckoutRepository
import javax.inject.Inject

class SaveProductsForCheckoutUseCase @Inject constructor(
    private val repository: CheckoutRepository
) {
    operator fun invoke(shoppingList: List<String>) {
        val productQuantity = shoppingList.groupingBy { it }.eachCount()
        repository.saveProducts(productQuantity.map { PreOrderProducts(it.key, it.value) })
    }
}