package com.nlambertucci.cabifychallenge.domain.model

import androidx.annotation.Keep

@Keep
data class Product(
    val code: String,
    val name: String,
    val price: Double,
    val currencySymbol: String,
    val image: String?,
    val discount: Discount?
)
