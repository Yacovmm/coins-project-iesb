package com.example.iesbcoinapp.domain

import com.example.iesbcoinapp.core.utils.ResponseWrapper
import com.example.iesbcoinapp.data.CoinRepository
import com.example.iesbcoinapp.data.database.CoinDao
import com.example.iesbcoinapp.data.database.models.CoinObject
import com.example.iesbcoinapp.data.network.ApiService
import com.example.iesbcoinapp.data.network.utils.RetrofitWrapper
import com.example.iesbcoinapp.domain.entities.CoinEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val coinDao: CoinDao,
    private val retrofitWrapper: RetrofitWrapper
) : CoinRepository {

    override suspend fun getLatestCoins(): ResponseWrapper<List<CoinEntity>> {
        val response = retrofitWrapper.request {
            service.getLatestPrices()
        }

        return when(response) {
            is ResponseWrapper.Success -> {
                val entities = CoinEntity.mapper(response.result.data)

                insertCoins(coins = entities)

                ResponseWrapper.Success(result = entities)
            }
            is ResponseWrapper.Error -> {
               response
            }
        }
    }

    override suspend fun insertCoins(coins: List<CoinEntity>) {
        coins.forEach { entity ->
            val coinObj = CoinObject.mapper(entity)

            coinDao.insertCoin(coinObj)
        }
    }

    override fun getCoinsFromDb(): Flow<List<CoinEntity>> {
        return coinDao.getAllCoins().map {
            CoinEntity.mapper(it)
        }
    }

    override suspend fun deleteAll() {
        coinDao.deleteAll()
    }

    override suspend fun favouriteCoin(id: Int, status: Boolean) {
        coinDao.favouriteCoin(status, id.toLong())
    }
}