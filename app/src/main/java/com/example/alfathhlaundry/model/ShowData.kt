package com.example.alfathhlaundry.model

import java.io.Serializable

data class ShowData(
    val id: Int,
    val nama: String,
    val baju: Int,
    val rok: Int,
    val jilbab: Int,
    val kaos: Int,
    val keterangan: String
) :Serializable