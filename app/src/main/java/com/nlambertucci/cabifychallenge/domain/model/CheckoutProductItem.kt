package com.nlambertucci.cabifychallenge.domain.model

import androidx.annotation.Keep

@Keep
data class CheckoutProductItem(
    val product: Product,
    val quantity: Int,
    val finalPrice: Double,
    val totalPrice: Double,
    val currencySymbol: String
)