package com.example.alfathhlaundry.data.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GrupSementara(
    val tanggal: String,
    val jam: String,
    val jenisPakaian: String,
    val kamar: String,
    val berat: Double,
    val jumlahOrang: Int
) : Parcelable