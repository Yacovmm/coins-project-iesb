package com.example.iesbcoinapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.*
import com.example.iesbcoinapp.core.utils.Constants.DATABASE_NAME
import com.example.iesbcoinapp.data.database.CoinDao
import com.example.iesbcoinapp.data.database.CoinDatabase
import com.example.iesbcoinapp.workmanager.CoinWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private fun getDataBaseCallback(context: Context) = object : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest =
                OneTimeWorkRequestBuilder<CoinWorker>().setConstraints(constraints)
                    .build()

            WorkManager.getInstance(context).enqueue(workRequest)
            println("Criei")
        }

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
        }
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CoinDatabase = Room.databaseBuilder(
        context,
        CoinDatabase::class.java,
        DATABASE_NAME
    ).addCallback(
        getDataBaseCallback(context)
    ).build()

    @Provides
    @Singleton
    fun provideCoinDao(
        database: CoinDatabase
    ): CoinDao = database.getCoinDao()

}