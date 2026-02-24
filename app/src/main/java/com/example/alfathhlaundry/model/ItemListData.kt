package com.example.alfathhlaundry.model

import java.io.Serializable

data class ItemListData (
    val berat: String,
    val judul: String,
    val nama: String,
    var status: Boolean = false
) :Serializable