package com.example.endclothing.data

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("products") val products: List<Product>,
    @SerializedName("title") val title: String,
    @SerializedName("product_count") val productCount: Int
)