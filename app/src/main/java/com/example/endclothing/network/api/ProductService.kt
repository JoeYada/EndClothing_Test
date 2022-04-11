package com.example.endclothing.network.api

import com.example.endclothing.data.ProductResponse
import com.example.endclothing.utils.PRODUCT_TEST_URL
import com.example.endclothing.utils.PRODUCT_URL
import retrofit2.http.GET

interface ProductService {
    @GET(PRODUCT_TEST_URL) suspend fun getTestProducts(): ProductResponse
    @GET(PRODUCT_URL) suspend fun getProducts(): ProductResponse
}