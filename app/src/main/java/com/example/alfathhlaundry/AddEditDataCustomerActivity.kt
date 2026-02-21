package com.example.alfathhlaundry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AddEditDataCustomerActivity : AppCompatActivity() {

    private lateinit var rvForm: RecyclerView
    private lateinit var btnSimpan: Button
    private lateinit var btnBack: ImageView
    private lateinit var tvTitle: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_data_customer)
    }
}