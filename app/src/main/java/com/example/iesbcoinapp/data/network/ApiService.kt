package com.example.iesbcoinapp.data.network

import com.example.iesbcoinapp.data.network.models.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("cryptocurrency/listings/latest")
    suspend fun getLatestPrices(
        @Query("start")
        start: Int = 1,
        @Query("limit")
        limit: Int = 16,
        @Query("convert")
        convert: String = "USD"
    ): Response<ApiResponse>


}