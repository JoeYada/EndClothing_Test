package com.example.endclothing.network.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.endclothing.data.Product
import com.example.endclothing.db.DatabaseProduct
import com.example.endclothing.network.api.ProductService
import com.example.endclothing.utils.OpenForTesting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OpenForTesting
class ProductRepository(private val service: ProductService, private val database: DatabaseProduct) {

    suspend fun getProducts(){
        withContext(Dispatchers.IO){
            val productTestResponse = service.getTestProducts()
            val productResponse = service.getProducts()
            val list = productResponse.products+productTestResponse.products
            val mutableList = list.toMutableList()
            mutableList.sortByDescending { it.image }
            database.productDao.insertAll(mutableList)
        }
    }

    val results: LiveData<List<Product>> = Transformations.map(database.productDao.getLocalDBProducts()){
        it
    }
}