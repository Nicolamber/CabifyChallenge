package com.nlambertucci.cabifychallenge.data.repository

import com.nlambertucci.cabifychallenge.data.remote.dto.ProductApiResponse
import com.nlambertucci.cabifychallenge.data.remote.ProductsApi
import com.nlambertucci.cabifychallenge.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productsApi: ProductsApi
) : ProductRepository {

    override suspend fun getProducts(): ProductApiResponse {
        return productsApi.getProducts()
    }
}