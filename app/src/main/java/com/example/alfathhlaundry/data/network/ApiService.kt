package com.example.alfathhlaundry.data.network

import AddGrupRequest
import GrupWithCustomer
import com.example.alfathhlaundry.data.model.response.ApiResponse
import com.example.alfathhlaundry.data.model.response.GrupListResponse
import com.example.alfathhlaundry.data.model.user.LoginRequest
import com.example.alfathhlaundry.data.model.user.LoginResponse
import com.example.alfathhlaundry.data.model.user.ShowDataResponse
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

    @FormUrlEncoded
    @PUT("grup/{id}")
    suspend fun updateStatus(
        @Path("id") id: Int,
        @Field("status_data") status: String // Pastikan key ini sama dengan $request->status_data di Laravel
    ): Response<ApiResponse>

    // Get grup by tanggal
    @GET("grup")
    suspend fun getGrup(
        @Query("tanggal") tanggal: String
    ): Response<GrupListResponse>

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

    // Search grup (konsisten pakai GrupListResponse)
    @GET("grup/search")
    suspend fun searchGrup(
        @Query("keyword") keyword: String
    ): Response<GrupListResponse>

    // Update grup
    @PUT("grup/{id}")
    suspend fun updateGrup(
        @Path("id") id: Int,
        @Body request: AddGrupRequest
    ): Response<ApiResponse>
}