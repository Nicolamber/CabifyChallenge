package com.nlambertucci.cabifychallenge.data.repository

import com.nlambertucci.cabifychallenge.domain.model.PreOrderProducts
import com.nlambertucci.cabifychallenge.domain.repository.CheckoutRepository

class CheckoutRepositoryImpl() : CheckoutRepository {

    private var products: List<PreOrderProducts> = listOf()

    override fun getProducts(): List<PreOrderProducts> {
        return products
    }

    override fun saveProducts(productsList: List<PreOrderProducts>) {
        products = productsList
    }
}