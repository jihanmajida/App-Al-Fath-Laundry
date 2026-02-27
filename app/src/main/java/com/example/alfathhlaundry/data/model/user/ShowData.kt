package com.example.alfathhlaundry.data.model.user

data class ShowDataResponse(
    val success: Boolean,
    val data: GrupData
)

data class GrupData(
    val id_grup: Int,
    val kamar: String,
    val jenis: String,
    val tanggal: String,
    val jam: String,
    val berat: Int,
    val data_customer: List<CustomerData>
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