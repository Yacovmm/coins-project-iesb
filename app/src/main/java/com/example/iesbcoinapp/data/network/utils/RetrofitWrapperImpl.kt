package com.example.iesbcoinapp.data.network.utils

import com.example.iesbcoinapp.core.utils.ResponseWrapper
import retrofit2.Response
import java.lang.Exception

class RetrofitWrapperImpl : RetrofitWrapper {

    override suspend fun <T> request(
        call: suspend () -> Response<T>
    ): ResponseWrapper<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                ResponseWrapper.Success<T>(result = response.body()!!)
            } else {
                ResponseWrapper.Error(response.message())
            }
        } catch (e: Exception) {
            ResponseWrapper.Error(e.message ?: "Erro inesperado")
        }
    }
}