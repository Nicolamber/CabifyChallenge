package com.nlambertucci.cabifychallenge.data.repository

import com.nlambertucci.cabifychallenge.data.remote.DiscountFakeApi
import com.nlambertucci.cabifychallenge.data.remote.ProductsApi
import com.nlambertucci.cabifychallenge.domain.model.Discount
import com.nlambertucci.cabifychallenge.domain.repository.DiscountRepository
import javax.inject.Inject

class DiscountRepositoryImpl @Inject constructor(
    private val discountApi: DiscountFakeApi
) : DiscountRepository {

    override fun getDiscountForProductId(id: String): Discount? {
        return discountApi.getDiscountByProductId(id)
    }
}