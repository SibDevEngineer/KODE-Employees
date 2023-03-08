package com.example.kodeemployees.data.api

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request()

        val requestBuilder = chain.request().newBuilder()
            .addHeader("Content-type", "application/json")

        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}