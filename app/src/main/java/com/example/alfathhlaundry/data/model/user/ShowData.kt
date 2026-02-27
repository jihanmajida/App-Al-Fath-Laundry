package com.example.alfathhlaundry.data.model.user

import GrupWithCustomer
import com.google.gson.annotations.SerializedName

data class ShowDataResponse(
    @SerializedName("result") val data: GrupWithCustomer
)

data class GrupData(
    @SerializedName("kamar") val kamar: String,
    @SerializedName("tanggal") val tanggal: String,
    @SerializedName("jam") val jam: String,
    @SerializedName("jenis_pakaian") val jenis: String, // Sebelumnya "jenis" mungkin null karena di JSON "jenis_pakaian"
    @SerializedName("berat") val berat: Double,
    @SerializedName("data_customer") val data_customer: List<CustomerData>
)

data class CustomerData(
    val id: Int,
    val id_pelanggan: Int,
    val nama_pelanggan: String,
    val baju: Int,
    val rok: Int,
    val jilbab: Int,
    val kaos: Int,
    val keterangan: String
)