package com.example.alfathhlaundry.data.model.response

import com.example.alfathhlaundry.data.model.user.ShowDataResponse

data class ApiResponse(
    val success: Boolean,
    val message: String,
    val data: ShowDataResponse
)