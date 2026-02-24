package com.example.alfathhlaundry.model


data class SearchData(
    val idGrup: Int,
    val namaKamar: String,
    val listNamaPelanggan: List<String>,
    val createdAt: Long // untuk urutan terbaru
)