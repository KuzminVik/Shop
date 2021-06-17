package ru.geekbrains.shopcatalog.repository

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class ResponseHeaderInterceptor(
    private val name: String,
    private val value: String
    ) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return response.newBuilder().header(name, value).build()
    }

}