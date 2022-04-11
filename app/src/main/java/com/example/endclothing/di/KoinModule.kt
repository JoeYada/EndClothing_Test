package com.example.endclothing.di

import android.app.Application
import androidx.compose.ui.graphics.vector.RootGroupName
import androidx.room.Room
import com.example.endclothing.db.DataConverter
import com.example.endclothing.db.DatabaseProduct
import com.example.endclothing.network.api.ProductService
import com.example.endclothing.network.repo.ProductRepository
import com.example.endclothing.utils.BASE_URL
import com.example.endclothing.views.MainProductViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//ViewModel Module
val viewModelModule = module {
    viewModel {
        MainProductViewModel(get())
    }
}

//Repository Module
val repositoryModule = module {
    single {
        ProductRepository(get(), get())
    }
}

//API Module
val apiModule = module {
    fun provideApi(retrofit: Retrofit):ProductService{
        return retrofit.create(ProductService::class.java)
    }
    single {
        provideApi(get())
    }
}

//Retrofit Module
val retrofitModule = module {
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        return client.addInterceptor(loggingInterceptor).build()
    }

    fun provideRetrofit(client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    single { provideLoggingInterceptor() }
    single { provideHttpClient(get()) }
    single { provideRetrofit(get()) }
}

//Database Module
val databaseModule = module {
    fun providesDatabase(app: Application): DatabaseProduct{
        return Room.databaseBuilder(app, DatabaseProduct::class.java, "product.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    single { providesDatabase(androidApplication()) }
}