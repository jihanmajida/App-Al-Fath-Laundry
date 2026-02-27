package com.example.alfathhlaundry.data.model.user

import java.io.Serializable

data class Grup(
    val id_grup: Int = 0,
    val id_user: Int = 0,
    val tanggal: String,
    val jam: String,
    val kamar: String,
    val berat: Double,
    val jenis_pakaian: String,
    val jumlah_orang: Int,
    var status_data: String? = null,   // 🔑 nullable

    val pelanggan: List<Pelanggan> = emptyList(),
    val detail_laundry: List<DetailLaundry> = emptyList()
) : Serializable