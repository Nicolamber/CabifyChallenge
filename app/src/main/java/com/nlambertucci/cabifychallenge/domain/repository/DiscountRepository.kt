package com.nlambertucci.cabifychallenge.domain.repository

import com.nlambertucci.cabifychallenge.domain.model.Discount

interface DiscountRepository {
    fun getDiscountForProductId(id: String): Discount?
}