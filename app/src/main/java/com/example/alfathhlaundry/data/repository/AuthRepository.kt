package com.example.alfathhlaundry.data.repository

import com.example.alfathhlaundry.data.model.user.LoginRequest
import com.example.alfathhlaundry.data.model.user.LoginResponse
import com.example.alfathhlaundry.data.network.ApiService
import retrofit2.Response

class AuthRepository(
    private val api: ApiService
) {

    suspend fun login(
        email: String,
        password: String
    ): Response<LoginResponse> {
        return api.login(LoginRequest(email, password))
    }
}