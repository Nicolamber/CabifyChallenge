package com.nlambertucci.cabifychallenge.data.remote.dto

import androidx.annotation.Keep
import com.nlambertucci.cabifychallenge.common.Constants
import com.nlambertucci.cabifychallenge.domain.model.Discount
import com.nlambertucci.cabifychallenge.domain.model.Product

@Keep
data class ProductDto(
    val code: String,
    val name: String,
    val price: Double
)

fun ProductDto.toProduct(image: String?, discount: Discount?): Product {
    return Product(
        code = code,
        name = name,
        price = price,
        currencySymbol = Constants.CURRENCY_SYMBOL,
        image = image,
        discount = discount
    )
}