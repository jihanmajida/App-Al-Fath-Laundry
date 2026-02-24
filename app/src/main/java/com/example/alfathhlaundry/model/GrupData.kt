package com.example.alfathhlaundry.model

import java.io.Serializable

data class GrupData(
    var tanggal: String,
    var jam: String,
    var seragam: String,
    var kamar: String,
    var berat: Double,
    var jumlahOrang: Int,
    var status: Boolean = false
) : Serializable