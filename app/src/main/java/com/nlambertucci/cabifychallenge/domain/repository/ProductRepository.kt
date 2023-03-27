package com.nlambertucci.cabifychallenge.domain.repository

import com.nlambertucci.cabifychallenge.data.remote.dto.ProductApiResponse


interface ProductRepository {
    suspend fun getProducts(): ProductApiResponse
}