package com.example.alfathhlaundry.data.repository

import AddGrupRequest
import GrupWithCustomer
import com.example.alfathhlaundry.data.model.response.ApiResponse
import com.example.alfathhlaundry.data.model.response.GrupListResponse
import com.example.alfathhlaundry.data.network.ApiService
import retrofit2.Response

class GrupRepository(private val api: ApiService) {

    // Load grup berdasarkan tanggal
    suspend fun getTodayData(tanggal: String): List<GrupWithCustomer> {
        val response: Response<GrupListResponse> = api.getGrup(tanggal)
        if (!response.isSuccessful) {
            throw Exception("Failed to load data: ${response.code()} ${response.message()}")
        }
        val body = response.body() ?: throw Exception("Response body null")
        return body.result
    }

    // Delete grup
    suspend fun deleteGrup(id: Int) {
        val response = api.deleteGrup(id)
        if (!response.isSuccessful) {
            throw Exception("Failed to delete: ${response.code()}")
        }
    }

    // Update status
    suspend fun updateStatus(id: Int, status: String) {
        val response = api.updateStatus(id, status)
        if (!response.isSuccessful) {
            throw Exception("Failed to update status: ${response.code()}")
        }
    }

    // Tambah grup
    suspend fun addGrup(request: Any) {
        val response: Response<ApiResponse> = api.addGrup(request as AddGrupRequest)
        if (!response.isSuccessful || response.body()?.success != true) {
            throw Exception(response.body()?.message ?: "Failed to add grup")
        }
    }

    // Update grup
    suspend fun updateGrup(id: Int, request: Any) {
        val response: Response<ApiResponse> = api.updateGrup(id, request as AddGrupRequest)
        if (!response.isSuccessful || response.body()?.success != true) {
            throw Exception(response.body()?.message ?: "Failed to update grup")
        }
    }

    // Optional: search grup
    suspend fun searchGrup(keyword: String): List<GrupWithCustomer> {
        val response = api.searchGrup(keyword)

        if (!response.isSuccessful) {
            throw Exception("Failed to search: ${response.code()}")
        }

        val body = response.body() ?: throw Exception("Response body null")

        return body.result   // ✅ langsung ambil result
    }
}