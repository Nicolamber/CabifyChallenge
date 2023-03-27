package com.nlambertucci.cabifychallenge.data.remote

import com.nlambertucci.cabifychallenge.common.Constants

/*
    Class created to simulate an API call and obtain a response for images associated with products.
     Ideally, there should be an endpoint to handle them or they should already be included in the response.
 */
class MockedImageApi {
    fun getMockedImageForProduct(id: String): String {
        return when (id) {
            Constants.VOUCHER -> "https://www.athenasbyolivia.com/uploads/1/4/4/4/14442588/s906586674800830865_p470_i5_w600.jpeg"
            Constants.TSHIRT -> "https://ombre.com/eng_pl_Mens-plain-t-shirt-violet-S1370-23814_3.jpg"
            Constants.MUG -> "https://goofy-shannon-8fec5b.netlify.app/mug.jpg"
            else -> "https://www.grouphealth.ca/wp-content/uploads/2018/05/placeholder-image.png"
        }
    }
}