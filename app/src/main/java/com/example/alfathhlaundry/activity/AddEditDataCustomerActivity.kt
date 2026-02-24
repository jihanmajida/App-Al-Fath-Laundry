package com.example.alfathhlaundry.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.DataStorage.DataStorage
import com.example.alfathhlaundry.adapter.FormAdapter
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.model.GrupData
import com.example.alfathhlaundry.model.GrupWithCustomer

class AddEditDataCustomerActivity : AppCompatActivity() {

    private lateinit var rvForm: RecyclerView
    private lateinit var btnSimpan: Button
    private lateinit var btnBack: ImageView
    private lateinit var tvTitle: TextView

    private lateinit var adapter: FormAdapter

    private var jumlah = 1

    private var mode: String? = null
    private var dataLama: GrupWithCustomer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_data_customer)

        // ambil mode
        mode = intent.getStringExtra("MODE")
        if (mode == "EDIT") {
            dataLama =
                intent.getSerializableExtra("DATA_GRUP") as GrupWithCustomer
        }

        initView()
        setupRecyclerView()
        setupTitle()
        setupClick()
    }

    private fun initView() {
        rvForm = findViewById(R.id.rvForm)
        btnSimpan = findViewById(R.id.btnSimpan)
        tvTitle = findViewById(R.id.tvTitle)
        btnBack = findViewById(R.id.btnBack)
    }

    private fun setupTitle() {
        if (mode == "EDIT") {
            tvTitle.text = "Edit Data Pelanggan"
        } else {
            tvTitle.text = "Tambah Data Pelanggan"
        }
    }

    private fun setupRecyclerView() {
        jumlah = intent.getIntExtra("JUMLAH", 1)
        adapter =
            FormAdapter(jumlah)
        rvForm.layoutManager =
            LinearLayoutManager(this)
        rvForm.adapter =
            adapter
    }

    private fun setupClick() {
        btnBack.setOnClickListener {
            finish()
        }
        btnSimpan.setOnClickListener {
            rvForm.clearFocus()
            val listCustomer =
                adapter.getAllData()
            if (listCustomer.any {
                    it.nama.isBlank()
                }) {
                Toast.makeText(
                    this,
                    "Nama tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            val grup =
                intent.getSerializableExtra("GRUP_OBJECT") as GrupData
            val dataGabungan =
                GrupWithCustomer(
                    grup,
                    listCustomer
                )

            // LOGIC ADD ATAU EDIT
            if (mode == "EDIT") {
                val index =
                    DataStorage.listGrup.indexOf(dataLama)
                if (index != -1) {
                    DataStorage.listGrup[index] =
                        dataGabungan
                }
            } else {
                DataStorage.listGrup.add(
                    dataGabungan
                )
            }
            Toast.makeText(
                this,
                "Data berhasil disimpan",
                Toast.LENGTH_SHORT
            ).show()
            finishAffinity()
            startActivity(
                Intent(
                    this,
                    HomeActivity::class.java
                )
            )
        }
    }
}