package com.example.iesbcoinapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.iesbcoinapp.data.database.models.CoinObject

@Database(
    entities = [CoinObject::class],
    version = 1
)
abstract class CoinDatabase : RoomDatabase() {

    abstract fun getCoinDao(): CoinDao

}