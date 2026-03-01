package com.example.alfathhlaundry.data.model.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Pelanggan(
    @SerializedName("id_pelanggan") val id_pelanggan: Int? = 0,
    @SerializedName("nama_pelanggan") var nama_pelanggan: String,
    @SerializedName("created_at") val created_at: String? = null, // Tambahkan
    @SerializedName("updated_at") val updated_at: String? = null  // Tambahkan
) : Parcelable