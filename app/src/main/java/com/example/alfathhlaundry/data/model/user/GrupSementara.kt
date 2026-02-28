package com.example.alfathhlaundry.data.model.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GrupSementara(
    @SerializedName("tanggal") val tanggal: String,
    @SerializedName("jam") val jam: String,
    @SerializedName("jenis_pakaian")val jenisPakaian: String,
    @SerializedName("kamar")val kamar: String,
    @SerializedName("berat") val berat: Double,
    @SerializedName("jumlah_orang") val jumlahOrang: Int
) : Parcelable

