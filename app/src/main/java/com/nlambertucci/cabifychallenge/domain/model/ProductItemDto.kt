package com.nlambertucci.cabifychallenge.domain.model

import androidx.annotation.Keep

@Keep
data class ProductItemDto(
    val action: String,
    val productSelected: Product
)