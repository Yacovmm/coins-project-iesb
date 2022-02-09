package com.example.iesbcoinapp.data.network.interceptors

import com.example.iesbcoinapp.core.utils.Constants.API_KEY
import com.example.iesbcoinapp.core.utils.Constants.API_KEY_HEADER
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Accepts", "application/json")
            .addHeader(API_KEY_HEADER, API_KEY)
            .build()
        return chain.proceed(request)
    }
}