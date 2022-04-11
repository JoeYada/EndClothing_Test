package com.example.endclothing.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.endclothing.data.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class DataConverter {

    @TypeConverter
    fun fromProduct(products: List<Product>): String {
        if(products.isEmpty()){
            return ""
        }
        val type = object : TypeToken<List<Product>>() {}.type
        return Gson().toJson(products, type)
    }

    @TypeConverter
    fun toProduct(product: String): List<Product>{
        val type = object : TypeToken<List<Product>>() {}.type
        return Gson().fromJson(product, type)
    }

}