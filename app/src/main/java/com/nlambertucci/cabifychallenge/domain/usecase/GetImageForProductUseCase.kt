package com.nlambertucci.cabifychallenge.domain.usecase

import com.nlambertucci.cabifychallenge.domain.repository.ImageRepository
import javax.inject.Inject

class GetImageForProductUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    operator fun invoke(id: String): String {
        return repository.getImageForProduct(id)
    }
}