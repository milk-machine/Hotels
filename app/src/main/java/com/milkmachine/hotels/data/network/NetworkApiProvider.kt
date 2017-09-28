package com.milkmachine.hotels.data.network

import com.google.gson.GsonBuilder
import com.milkmachine.hotels.API_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

class NetworkApiProvider @Inject constructor() : Provider<NetworkApi> {

    override fun get(): NetworkApi {
        val retrofit = createRetrofit(createHttpClient(createLoggingInterceptor()), createConverterFactory())
        return retrofit.create(NetworkApi::class.java)
    }

    private fun createConverterFactory(): Converter.Factory {
        val gson = GsonBuilder().create()
        return GsonConverterFactory.create(gson)
    }

    private fun createRetrofit(httpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit =
            Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(converterFactory)
                    .client(httpClient)
                    .baseUrl(API_URL)
                    .build()

    private fun createHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }
}