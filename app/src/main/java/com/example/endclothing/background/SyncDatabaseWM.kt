package com.example.endclothing.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.endclothing.network.repo.ProductRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.lang.Exception

class SyncDatabaseWM(context: Context, params: WorkerParameters): CoroutineWorker(context, params),KoinComponent {

    val dataSyncRepo: ProductRepository by inject()

    companion object{
        const val WORK_NAME = "com.example.endclothing.background.SyncDatabaseWM"
    }

    override suspend fun doWork(): Result {
        try {
            dataSyncRepo.getProducts()
        }catch (e: Exception){
            return Result.retry()
        }
        return Result.success()
    }
}