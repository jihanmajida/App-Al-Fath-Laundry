package com.example.alfathhlaundry.data.model.user

import java.io.Serializable

data class DetailLaundry(
    val id: Int? = null,
    val id_grup: Int,
    val id_pelanggan: Int,
    var baju: Int,
    var rok: Int,
    var jilbab: Int,
    var kaos: Int,
    var keterangan: String?,
    val created_at: String? = null, // Tambahkan ini
    val updated_at: String? = null  // Tambahkan ini
) : Serializable