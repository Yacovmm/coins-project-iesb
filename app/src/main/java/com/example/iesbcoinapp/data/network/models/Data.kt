package com.example.iesbcoinapp.data.network.models

data class Data(
    val id: Int,
    val circulating_supply: Double,
    val cmc_rank: Double,
    val date_added: String,
    val last_updated: String,
    val max_supply: Double,
    val name: String,
    val num_market_pairs: Double,
    val platform: Any,
    val quote: Quote,
    val slug: String,
    val symbol: String,
    val tags: List<String>,
    val total_supply: Double
)
