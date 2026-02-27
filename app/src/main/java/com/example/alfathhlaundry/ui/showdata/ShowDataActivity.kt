package com.example.alfathhlaundry.ui.showdata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.ui.adapter.ShowDataAdapter
import com.example.alfathhlaundry.data.network.RetrofitClient
import com.example.alfathhlaundry.data.repository.ShowDataRepository

class ShowDataActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvWaktu: TextView
    private lateinit var tvJenis: TextView
    private lateinit var tvBerat: TextView
    private lateinit var rvPelanggan: RecyclerView
    private lateinit var btnBack: ImageButton

    private lateinit var viewModel: ShowDataViewModel
    private lateinit var adapter: ShowDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_data)

        initView()
        setupRecyclerView()
        setupViewModel()
        observeData()
        setupBack()

        val idGrup = intent.getIntExtra("ID_GRUP", 0)

        if (idGrup != 0) {
            viewModel.getShowData(idGrup)
        } else {
            finish() // kalau id tidak ada, tutup activity
        }
    }

    private fun initView() {
        tvTitle = findViewById(R.id.tvTitle)
        tvWaktu = findViewById(R.id.tvWaktu)
        tvJenis = findViewById(R.id.tvJenis)
        tvBerat = findViewById(R.id.tvBerat)
        rvPelanggan = findViewById(R.id.rvPelanggan)
        btnBack = findViewById(R.id.btnBack)
    }

    private fun setupRecyclerView() {
        adapter = ShowDataAdapter(emptyList())
        rvPelanggan.layoutManager = LinearLayoutManager(this)
        rvPelanggan.adapter = adapter
    }

    private fun setupViewModel() {
        val repository = ShowDataRepository(RetrofitClient.getInstance(this))
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ShowDataViewModel::class.java]
    }

    private fun observeData() {
        viewModel.showData.observe(this) { data ->
            if (data == null) {
                android.util.Log.e("SHOW_DATA", "Data null dari API")
                return@observe
            }

            // 1. Set data Header
            tvTitle.text = data.kamar
            tvWaktu.text = "${data.tanggal} ${data.jam}"

            // GUNAKAN 'jenis_pakaian' sesuai model GrupWithCustomer
            tvJenis.text = data.jenis_pakaian
            tvBerat.text = "${data.berat} Kg"

            // 2. Update RecyclerView Pelanggan
            // GUNAKAN 'pelanggan' karena di Controller Laravel: with('pelanggan')
            val listPelanggan = data.pelanggan

            android.util.Log.d("SHOW_DATA", "Jumlah Pelanggan: ${listPelanggan.size}")

            if (listPelanggan.isNotEmpty()) {
                adapter.updateData(listPelanggan)
            } else {
                android.util.Log.w("SHOW_DATA", "List pelanggan kosong!")
            }
        }
    }

    private fun setupBack() {
        btnBack.setOnClickListener {
            finish()
        }
    }
}