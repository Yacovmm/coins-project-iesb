package com.example.iesbcoinapp.di

import android.content.Context
import androidx.room.Room
import com.example.iesbcoinapp.core.utils.Constants.DATABASE_NAME
import com.example.iesbcoinapp.data.database.CoinDao
import com.example.iesbcoinapp.data.database.CoinDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CoinDatabase = Room.databaseBuilder(
        context,
        CoinDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideCoinDao(
        database: CoinDatabase
    ): CoinDao = database.getCoinDao()

}