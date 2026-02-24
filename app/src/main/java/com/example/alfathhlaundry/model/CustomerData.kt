package com.example.alfathhlaundry.model

import java.io.Serializable

data class CustomerData(
    var nama: String,
    var baju: Int,
    var rok: Int,
    var jilbab: Int,
    var kaos: Int,
    var keterangan: String
) :Serializable