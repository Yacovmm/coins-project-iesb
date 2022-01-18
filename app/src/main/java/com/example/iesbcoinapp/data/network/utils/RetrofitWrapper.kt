package com.example.iesbcoinapp.data.network.utils

import com.example.iesbcoinapp.core.utils.ResponseWrapper
import retrofit2.Response

interface RetrofitWrapper {

    suspend fun <T> request(
        call: suspend () -> Response<T>
    ): ResponseWrapper<T>

}