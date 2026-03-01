package com.example.alfathhlaundry.data.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://alfath-laundry.projects.my.id/api/"

    fun getInstance(context: Context): ApiService {

        // 1. Tambahkan Logging Interceptor agar bisa debug API di Logcat
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging) // Pasang logging
            .addInterceptor { chain ->
                // 2. Ambil token dari SharedPreferences "SESSION"
                val prefs = context.getSharedPreferences("SESSION", Context.MODE_PRIVATE)
                val token = prefs.getString("TOKEN", null)

                val requestBuilder = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")

                // 3. Jika token ada, selipkan ke Header Authorization
                if (!token.isNullOrEmpty()) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }

                chain.proceed(requestBuilder.build())
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}