package com.nlambertucci.cabifychallenge.data.remote

import com.nlambertucci.cabifychallenge.common.Constants
import com.nlambertucci.cabifychallenge.data.remote.dto.ProductApiResponse
import retrofit2.http.GET

interface ProductsApi {
    @GET(Constants.PRODUCT_ENDPOINT)
    suspend fun getProducts(): ProductApiResponse
}