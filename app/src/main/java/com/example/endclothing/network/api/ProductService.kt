package com.example.endclothing.network.api

import com.example.endclothing.PRODUCT_URL
import com.example.endclothing.data.ProductResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ProductService {
    @GET(PRODUCT_URL) fun getProducts(): Flow<ProductResponse>
}