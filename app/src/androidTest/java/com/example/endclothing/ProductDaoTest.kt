package com.example.endclothing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.endclothing.data.Product
import com.example.endclothing.db.DatabaseProduct
import com.example.endclothing.db.ProductDao
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ProductDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: DatabaseProduct

    private lateinit var dao: ProductDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DatabaseProduct::class.java
        ).allowMainThreadQueries().build()
        dao = database.productDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertProduct() = runBlockingTest {
        val productItem = Product(1,"Test Shirt",
            "£199","https://media.endclothing.com/media/f_auto,q_auto:eco/prodmedia/media/catalog/product/2/2/22-02-2021_MO_HB8059_1_1.jpg")

        dao.insertAll(listOf(productItem))

        val allProduct = dao.getLocalDBProducts().value?.toList()

        assertThat(allProduct).contains(productItem)
    }
}