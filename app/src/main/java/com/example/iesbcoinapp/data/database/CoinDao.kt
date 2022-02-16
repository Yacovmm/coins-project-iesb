package com.example.iesbcoinapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.iesbcoinapp.data.database.models.CoinObject
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoin(coinObject: CoinObject)


    @Query("SELECT * FROM coin_table ORDER BY marketCap DESC")
    fun getAllCoins(): Flow<List<CoinObject>>

    @Query("DELETE FROM coin_table")
    suspend fun deleteAll()

    @Query("UPDATE coin_table SET isFavourite=:status WHERE id=:id")
    suspend fun favouriteCoin(status: Boolean, id: Long)

}