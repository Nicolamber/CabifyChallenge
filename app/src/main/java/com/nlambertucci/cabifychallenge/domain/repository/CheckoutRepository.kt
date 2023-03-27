package com.nlambertucci.cabifychallenge.domain.repository

import com.nlambertucci.cabifychallenge.domain.model.PreOrderProducts

interface CheckoutRepository {
    fun getProducts(): List<PreOrderProducts>
    fun saveProducts(productsList: List<PreOrderProducts>)
}