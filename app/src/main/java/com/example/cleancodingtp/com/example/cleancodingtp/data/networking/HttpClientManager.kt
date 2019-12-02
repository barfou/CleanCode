package com.example.cleancodingtp.com.example.cleancodingtp.data.networking

import com.example.cleancodingtp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private class HttpClientManagerImpl : HttpClientManager {

    private val okHttpClient = OkHttpClient
        .Builder()
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    HttpLoggingInterceptor().apply {
                        this.level = HttpLoggingInterceptor.Level.BODY
                    }
                )
            }
        }
        .build()

    override val retrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory((GsonConverterFactory.create()))
        .build()
}

interface HttpClientManager {

    val retrofit: Retrofit

    companion object {
        val instance: HttpClientManager = HttpClientManagerImpl()
    }
}

inline fun <reified Api> HttpClientManager.createApi(): Api {
    return retrofit.create()
}