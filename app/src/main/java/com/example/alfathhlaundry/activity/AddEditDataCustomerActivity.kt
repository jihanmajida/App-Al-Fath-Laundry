package com.example.alfathhlaundry.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.adapter.FormAdapter
import com.example.alfathhlaundry.model.FormData
import com.example.alfathhlaundry.R

class AddEditDataCustomerActivity : AppCompatActivity() {

    private lateinit var rvForm: RecyclerView
    private lateinit var btnSimpan: Button
    private lateinit var btnBack: ImageView
    private lateinit var tvTitle: TextView

    private lateinit var adapter: FormAdapter
    private var mode = "ADD"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_data_customer)

        initView()
        setupRecyclerView()
        setupMode()
        setupClick()
    }

    private fun initView() {
        rvForm = findViewById(R.id.rvForm)
        btnSimpan = findViewById(R.id.btnSimpan)
        btnBack = findViewById(R.id.btnBack)
        tvTitle = findViewById(R.id.tvTitle)
    }

    private fun setupRecyclerView() {
        val jumlahForm = intent.getIntExtra("JUMLAH", 0)
        Log.i("Jumlah", jumlahForm.toString())
        adapter = FormAdapter(formLength = jumlahForm)
        rvForm.layoutManager = LinearLayoutManager(this)
        rvForm.adapter = adapter
    }

    private fun setupMode() {
        mode = intent.getStringExtra("MODE") ?: "ADD"

        if (mode == "EDIT") {
            tvTitle.text = "Edit Data Group"

//            val data = FormData(
//                nama = intent.getStringExtra("NAMA") ?: "",
//                baju = intent.getIntExtra("BAJU", 0),
//                rok = intent.getIntExtra("ROK", 0),
//                jilbab = intent.getIntExtra("JILBAB", 0),
//                kaos = intent.getIntExtra("KAOS", 0),
//                keterangan = intent.getStringExtra("KETERANGAN") ?: ""
//            )
//            rvForm.post {
//                adapter.setData(data)
//            }
        } else {
            tvTitle.text = "Tambah Data Group"
        }
    }

    private fun setupClick() {

        btnBack.setOnClickListener { finish() }

        btnSimpan.setOnClickListener {

            val data = adapter.getData()

            if (data.nama.isEmpty()) {
                return@setOnClickListener
            }

            if (mode == "EDIT") {
                // update
            } else {
                // insert
            }
            finish()
        }
    }
}