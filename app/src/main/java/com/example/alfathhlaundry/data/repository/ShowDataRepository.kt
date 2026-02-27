package com.example.alfathhlaundry.data.repository

import com.example.alfathhlaundry.data.model.user.ShowDataResponse
import com.example.alfathhlaundry.data.network.ApiService

class ShowDataRepository(private val apiService: ApiService) {

    suspend fun getShowData(id: Int): ShowDataResponse? {
        val response = apiService.getShowData(id)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}