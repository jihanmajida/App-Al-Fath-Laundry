package com.example.alfathhlaundry.data.network

import AddGrupRequest
import GrupWithCustomer
import com.example.alfathhlaundry.data.model.response.ApiResponse
import com.example.alfathhlaundry.data.model.response.GrupListResponse
import com.example.alfathhlaundry.data.model.user.LoginRequest
import com.example.alfathhlaundry.data.model.user.LoginResponse
import com.example.alfathhlaundry.data.model.user.ShowDataResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // ----------------- AUTH -----------------
    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    // ----------------- GRUP -----------------
    // Add grup
    @POST("grup")
    suspend fun addGrup(
        @Body request: AddGrupRequest
    ): Response<ApiResponse>


    // Optional: DELETE RESTful jika server support
    @DELETE("grup/{id}")
    suspend fun deleteGrup(@Path("id") id: Int): Response<ApiResponse>

    @POST("grup/{id}/update-status") // Pastikan tidak ada titik dua (:) di dalam kurung kurawal
    suspend fun updateStatus(
        @Path("id") id: Int,
        @Body body: Map<String, String>
    ): Response<ResponseBody>

    // Get grup by tanggal
    @GET("grup")
    suspend fun getGrup(
        @Query("tanggal") tanggal: String
    ): Response<List<GrupWithCustomer>>

    // Get detail grup by id (tampilkan show data)
    @GET("grup/{id}")
    suspend fun getShowData(
        @Path("id") id: Int
    ): Response<ShowDataResponse>

    // Get full detail grup untuk edit
    @GET("grup/{id}/detail")
    suspend fun getDetail(
        @Path("id") id: Int
    ): Response<GrupWithCustomer>

    // Search grup
    @GET("grup")
    suspend fun searchGrup(
        @Query("nama") nama: String?,
        @Query("tanggal") tanggal: String?
    ): Response<List<GrupWithCustomer>>

    // Update grup
    @PUT("grup/{id}")
    suspend fun updateGrup(
        @Path("id") id: Int,
        @Body request: AddGrupRequest
    ): Response<ApiResponse>
}