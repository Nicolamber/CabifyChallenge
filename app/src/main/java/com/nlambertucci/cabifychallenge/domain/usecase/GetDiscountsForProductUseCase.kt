package com.nlambertucci.cabifychallenge.domain.usecase

import com.nlambertucci.cabifychallenge.domain.model.Discount
import com.nlambertucci.cabifychallenge.domain.repository.DiscountRepository
import javax.inject.Inject

class GetDiscountsForProductUseCase @Inject constructor(
    private val repository: DiscountRepository
) {

    operator fun invoke(productId: String): Discount? {
        return repository.getDiscountForProductId(productId)
    }
}