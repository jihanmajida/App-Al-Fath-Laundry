package com.example.alfathhlaundry.model

import java.io.Serializable

data class GrupWithCustomer(
    val grup: GrupData,
    val customers: List<CustomerData>
) : Serializable