package com.example.alfathhlaundry.data.model.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Pelanggan(
    @SerializedName("id_pelanggan") val id_pelanggan: Int? = 0, // Tambahkan ini untuk Gambar 4
    @SerializedName("nama_pelanggan") var nama_pelanggan: String,
    @SerializedName("baju") val baju: Int? = 0,
    @SerializedName("rok") val rok: Int? = 0,
    @SerializedName("jilbab") val jilbab: Int? = 0,
    @SerializedName("kaos") val kaos: Int? = 0,
    @SerializedName("keterangan") val keterangan: String? = ""
) : Parcelable