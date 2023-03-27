package com.nlambertucci.cabifychallenge.data.repository

import com.nlambertucci.cabifychallenge.data.remote.MockedImageApi
import com.nlambertucci.cabifychallenge.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageApi: MockedImageApi
) : ImageRepository {

    override fun getImageForProduct(productId: String): String {
        return imageApi.getMockedImageForProduct(productId)
    }
}