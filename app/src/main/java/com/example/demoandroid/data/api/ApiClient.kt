package com.example.demoandroid.data.api

import android.content.Context
import com.example.demoandroid.utils.Constants
import com.example.demoandroid.data.api.services.MarvelApiServices
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    fun getService(context: Context?): MarvelApiServices {
        //val preferencesManager = PreferencesManager(context, Constants.TOKEN_SESSION)

        val serviceLogging = HttpLoggingInterceptor()
        serviceLogging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
        client.connectTimeout(30, TimeUnit.SECONDS)
        client.readTimeout(30, TimeUnit.SECONDS)
        client.addInterceptor(serviceLogging)
        client.addInterceptor { chain: Interceptor.Chain ->
            val requestBuilder = chain.request().newBuilder()
            //requestBuilder.addHeader("Authorization", preferencesManager.tokenSession) // De ser necesario
            chain.proceed(requestBuilder.build())
        }
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
        return retrofit.create(MarvelApiServices::class.java)
    }

    companion object {
        private const val API_BASE_URL = "http://gateway.marvel.com/v1/public/"
        val servicesMarvel: MarvelApiServices
            get() = builder.build().create(MarvelApiServices::class.java)

        private val httpClient =
            OkHttpClient.Builder().readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
        private val logging = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        private val builder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                httpClient.addInterceptor(logging).build()
            )
            .baseUrl(API_BASE_URL)
    }
}