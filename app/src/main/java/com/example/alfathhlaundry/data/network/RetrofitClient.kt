package com.example.alfathhlaundry.data.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2:8000/api/"

    fun getInstance(context: Context): ApiService {

        val client = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->

                val prefs = context.getSharedPreferences("SESSION", Context.MODE_PRIVATE)
                val token = prefs.getString("TOKEN", null)

                val requestBuilder = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")

                if (!token.isNullOrEmpty()) {
                    requestBuilder.addHeader(
                        "Authorization",
                        "Bearer $token"
                    )
                }

                chain.proceed(requestBuilder.build())
            })
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}