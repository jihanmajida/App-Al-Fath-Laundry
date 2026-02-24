package com.example.alfathhlaundry.model

import java.io.Serializable

data class ShowDataResponse(
    val title: String,
    val waktu: String,
    val jenis_seragam: String,
    val berat: String,
    val pelanggan: List<ShowData>
) : Serializable