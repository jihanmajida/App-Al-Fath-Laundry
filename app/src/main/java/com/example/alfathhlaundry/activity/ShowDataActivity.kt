package com.example.alfathhlaundry.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.adapter.ShowDataAdapter
import com.example.alfathhlaundry.model.GrupWithCustomer

class ShowDataActivity : AppCompatActivity() {


    private lateinit var tvTitle: TextView
    private lateinit var tvWaktu: TextView
    private lateinit var tvJenis: TextView
    private lateinit var tvBerat: TextView
    private lateinit var rvPelanggan: RecyclerView
    private lateinit var btnBack: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_data)

        initView()
        getData()
        setupBack()
    }

    private fun initView(){
        tvTitle = findViewById(R.id.tvTitle)
        tvWaktu = findViewById(R.id.tvWaktu)
        tvJenis = findViewById(R.id.tvJenis)
        tvBerat = findViewById(R.id.tvBerat)
        rvPelanggan = findViewById(R.id.rvPelanggan)
        btnBack = findViewById(R.id.btnBack)
    }

    private fun getData(){

        val data =
            intent.getSerializableExtra("DATA_GRUP") as? GrupWithCustomer

        data?.let {

            tvTitle.text = it.grup.kamar
            tvWaktu.text = it.grup.jam
            tvJenis.text = it.grup.seragam
            tvBerat.text = "${it.grup.berat} Kg"

            // DEBUG CEK JUMLAH
            println("JUMLAH CUSTOMER = ${it.customers.size}")

            rvPelanggan.layoutManager =
                LinearLayoutManager(this)

            rvPelanggan.adapter =
                ShowDataAdapter(it.customers)

        }

    }

    private fun setupBack() {
        btnBack.setOnClickListener {
            finish()
        }
    }
}