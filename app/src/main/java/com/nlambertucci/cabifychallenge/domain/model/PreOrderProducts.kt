package com.nlambertucci.cabifychallenge.domain.model

import androidx.annotation.Keep

@Keep
data class PreOrderProducts(
    val code: String,
    val quantity: Int
)
