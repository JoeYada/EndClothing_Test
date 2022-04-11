package com.example.endclothing.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.endclothing.data.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    fun getLocalDBProducts():LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<Product>)
}

@Database(entities = [Product::class], version = 1, exportSchema = false)
//@TypeConverters(DataConverter::class)
abstract class DatabaseProduct: RoomDatabase(){
    abstract val productDao: ProductDao
}