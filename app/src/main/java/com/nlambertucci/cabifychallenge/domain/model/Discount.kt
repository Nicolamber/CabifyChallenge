package com.nlambertucci.cabifychallenge.domain.model

sealed class Discount {
    data class GetDiscountByPercentage(
        val products: Int,
        val percentage: Float
    ): Discount()

    data class Get2x1Discount(
        val type: String,
        val percentage: Float
    ): Discount()
}
