package com.nlambertucci.cabifychallenge.data.remote

import com.nlambertucci.cabifychallenge.common.Constants
import com.nlambertucci.cabifychallenge.domain.model.Discount

/*
    Class created to simulate an API call and obtain a response for discounts. Ideally, there should be an endpoint to handle them.
 */
class DiscountFakeApi {
    fun getDiscountByProductId(id: String): Discount? {
        return when (id) {
            Constants.VOUCHER -> Discount.Get2x1Discount("pairDiscount", 0.5f)
            Constants.TSHIRT -> Discount.GetDiscountByPercentage(3,0.05f)
            else -> null
        }
    }
}