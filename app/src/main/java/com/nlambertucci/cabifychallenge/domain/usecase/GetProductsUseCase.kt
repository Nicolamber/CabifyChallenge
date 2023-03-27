package com.nlambertucci.cabifychallenge.domain.usecase

import com.nlambertucci.cabifychallenge.common.BaseResponse
import com.nlambertucci.cabifychallenge.data.remote.dto.ProductDto
import com.nlambertucci.cabifychallenge.data.remote.dto.toProduct
import com.nlambertucci.cabifychallenge.domain.model.Product
import com.nlambertucci.cabifychallenge.domain.repository.ProductRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {

    operator fun invoke() = flow {
        emit(BaseResponse.Loading<List<ProductDto>>())
        runCatching {
            repository.getProducts().products
        }.onSuccess { response ->
            emit(BaseResponse.Success<List<ProductDto>>(response))
        }.onFailure {
            emit(BaseResponse.Error<List<ProductDto>>("Ocurrió un problema al cargar la pagina. Vuelve a intentarlo maás tarde."))
        }
    }
}