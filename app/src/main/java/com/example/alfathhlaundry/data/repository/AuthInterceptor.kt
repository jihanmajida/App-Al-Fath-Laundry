package com.example.alfathhlaundry.data.repository

import com.example.alfathhlaundry.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = TokenManager.getToken()

        val request = chain.request().newBuilder()

        if (!token.isNullOrEmpty()) {
            request.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(request.build())
    }
}