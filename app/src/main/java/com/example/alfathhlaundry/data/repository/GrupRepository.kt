package com.example.alfathhlaundry.data.repository

import AddGrupRequest
import GrupWithCustomer
import android.util.Log
import com.example.alfathhlaundry.data.model.response.ApiResponse
import com.example.alfathhlaundry.data.model.response.GrupListResponse
import com.example.alfathhlaundry.data.network.ApiService
import retrofit2.Response

class GrupRepository(private val api: ApiService) {

    // Load grup berdasarkan tanggal
    suspend fun getTodayData(tanggal: String): List<GrupWithCustomer> {
        val response = api.getGrup(tanggal)
        return if (response.isSuccessful) {
            val rawData = response.body() ?: emptyList()
            // Memastikan list pelanggan & detail tidak null sebelum dikirim ke UI
            rawData.map {
                it.copy(
                    pelanggan = it.pelanggan ?: emptyList(),
                    detail_laundry = it.detail_laundry ?: emptyList()
                )
            }
        } else {
            emptyList()
        }
    }

    // Search grup: SEKARANG KONSISTEN MENGGUNAKAN List<GrupWithCustomer>
    suspend fun searchGrup(nama: String?, tanggal: String?): List<GrupWithCustomer> {
        try {
            val response = api.searchGrup(nama ?: "", tanggal)

            if (response.isSuccessful) {
                val rawData = response.body() ?: emptyList()
                // Memastikan list pelanggan & detail tidak null
                return rawData.map {
                    it.copy(
                        pelanggan = it.pelanggan ?: emptyList(),
                        detail_laundry = it.detail_laundry ?: emptyList()
                    )
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("SEARCH_ERROR", "Response Error: $errorBody")
                throw Exception("Failed to search: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("SEARCH_ERROR", "Exception: ${e.message}")
            throw e
        }
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
        // Key harus "status_data" (pake underscore), bukan "statusData"
        val body = mapOf("status_data" to status)
        val response = api.updateStatus(id, body)

        if (!response.isSuccessful) {
            val errorPesat = response.errorBody()?.string()
            Log.e("API_ERROR", "Pesan dari Server: $errorPesat")
            throw Exception("Gagal: ${response.code()}")
        }
    }

    // Tambah grup
    suspend fun addGrup(request: AddGrupRequest): String {
        val response = api.addGrup(request)
        if (response.isSuccessful) {
            return "Berhasil"
        } else {
            // Ini akan memunculkan pesan asli dari Laravel (misal: "status_data is required")
            val errorDetail = response.errorBody()?.string() ?: "Unknown"
            Log.e("API_TEST", "ALASAN GAGAL: $errorDetail")
            return "Gagal: $errorDetail"
        }
    }

    // Update grup
    suspend fun updateGrup(id: Int, request: AddGrupRequest) {
        val response = api.updateGrup(id, request)
        if (response.isSuccessful) {
            val body = response.body()
            if (body?.success != true) {
                // LOG THE REAL MESSAGE HERE
                Log.e("API_TEST", "Server rejected because: ${body?.message}")
                throw Exception(body?.message ?: "Server rejected update")
            }
        } else {
            Log.e("API_TEST", "HTTP Error: ${response.code()} - ${response.errorBody()?.string()}")
            throw Exception("HTTP Error: ${response.code()}")
        }
    }
}