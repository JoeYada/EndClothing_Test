package com.example.endclothing.di

import android.app.Application
import androidx.work.*
import com.example.endclothing.background.SyncDatabaseWM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.util.concurrent.TimeUnit

class EndClothingApp: Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        /**
         * Start Koin
        */
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@EndClothingApp)
            modules(listOf(retrofitModule, apiModule, repositoryModule, viewModelModule, databaseModule))
        }

        delayedInit()
    }

    private fun delayedInit(){
        applicationScope.launch {
            setUpReccuringWork()
        }
    }

    /**
     * Constraints in which your WM will run
     * WM background job to fetch new network data daily
    */
    private fun setUpReccuringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(true)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<SyncDatabaseWM>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            SyncDatabaseWM.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}