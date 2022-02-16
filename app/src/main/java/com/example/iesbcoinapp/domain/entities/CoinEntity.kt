package com.example.iesbcoinapp.domain.entities

import com.example.iesbcoinapp.data.database.models.CoinObject
import com.example.iesbcoinapp.data.network.models.Data

data class CoinEntity(
    val id: Int,
    val name: String,
    val marketCap: Double,
    val price: Double,
    val update_at: String = "",
    val icon: String = "https://s2.coinmarketcap.com/static/img/coins/64x64/$id.png",
    val isFavourite: Boolean = false
) {

    companion object {

        fun mapper(data: List<Data>): List<CoinEntity> {
            return data.map { data ->
                CoinEntity(
                    id = data.id,
                    name = data.name,
                    marketCap = data.quote.USD.market_cap,
                    price = data.quote.USD.price
                )
            }
        }

        @JvmName("mapper_from_object")
        fun mapper(objectsData: List<CoinObject>): List<CoinEntity> {
            return objectsData.map { obj ->
                CoinEntity(
                    id = obj.id.toInt(),
                    name = obj.name,
                    marketCap = obj.marketCap,
                    price = obj.price,
                    icon = obj.iconUrl,
                    update_at = obj.created_at,
                    isFavourite = obj.isFavourite
                )
            }
        }

    }

}