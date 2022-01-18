package com.example.iesbcoinapp.data.database.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
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

    }

}
