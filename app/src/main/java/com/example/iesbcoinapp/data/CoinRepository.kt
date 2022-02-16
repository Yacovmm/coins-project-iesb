package com.example.iesbcoinapp.data

import com.example.iesbcoinapp.core.utils.ResponseWrapper
import com.example.iesbcoinapp.domain.entities.CoinEntity
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    suspend fun getLatestCoins(): ResponseWrapper<List<CoinEntity>>

    suspend fun insertCoins(coins: List<CoinEntity>)

    fun getCoinsFromDb(): Flow<List<CoinEntity>>

    suspend fun deleteAll()

    suspend fun favouriteCoin(id: Int, status: Boolean)

}