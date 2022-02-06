package com.example.iesbcoinapp.data.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.iesbcoinapp.domain.entities.CoinEntity
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "coin_table")
data class CoinObject constructor(
    @PrimaryKey
    var id: Long = 0,
    var name: String = "",
    var marketCap: Double = 0.0,
    var price: Double = 0.0,
    var iconUrl: String = "",
    var created_at: String = getCurrentDate()
): Serializable {

    companion object {

        private fun getCurrentDate(): String {
            val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val date = Date()
            return formatter.format(date)
        }

        fun mapper(coin: CoinEntity): CoinObject {
            return CoinObject(
                id = coin.id.toLong(),
                name = coin.name,
                marketCap = coin.marketCap,
                price = coin.price,
                iconUrl = coin.icon
            )
        }

    }

}
