package com.nlambertucci.cabifychallenge.domain.repository

interface ImageRepository {
    fun getImageForProduct(productId: String): String
}