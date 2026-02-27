package com.example.alfathhlaundry.utils

object TokenManager {

    private var token: String? = null

    fun saveToken(newToken: String) {
        token = newToken
    }

    fun getToken(): String? {
        return token
    }
}