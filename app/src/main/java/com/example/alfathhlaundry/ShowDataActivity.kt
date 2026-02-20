package com.example.alfathhlaundry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShowDataActivity : AppCompatActivity() {


    private lateinit var rvPelanggan: RecyclerView
    private lateinit var btnBack: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_data)

        rvPelanggan = findViewById(R.id.rvPelanggan)
        btnBack = findViewById(R.id.btnBack)

        rvPelanggan.layoutManager = LinearLayoutManager(this)

        btnBack.setOnClickListener { finish() }

        fetchData()
    }

    private fun fetchData() {
    }
}