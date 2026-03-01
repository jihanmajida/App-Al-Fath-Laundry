package com.example.alfathhlaundry.ui.customer

import AddGrupRequest
import DetailLaundryRequest
import GrupWithCustomer
import PelangganRequest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.data.model.user.*
import com.example.alfathhlaundry.data.network.RetrofitClient
import com.example.alfathhlaundry.data.repository.GrupRepository
import com.example.alfathhlaundry.ui.adapter.FormAdapter
import com.example.alfathhlaundry.ui.grup.GrupViewModel
import com.example.alfathhlaundry.ui.grup.GrupViewModelFactory
import com.example.alfathhlaundry.ui.home.HomeActivity
import java.text.SimpleDateFormat
import java.util.Locale

class AddEditDataCustomerActivity : AppCompatActivity() {

    private lateinit var rvForm: RecyclerView
    private lateinit var btnSimpan: Button
    private lateinit var btnBack: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var adapter: FormAdapter

    private var mode: String = "ADD"
    private var jumlah: Int = 1
    private var grupSementara: GrupSementara? = null
    private var dataGrup: GrupWithCustomer? = null

    private val viewModel: GrupViewModel by viewModels {
        GrupViewModelFactory(GrupRepository(RetrofitClient.getInstance(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_data_customer)

        mode = intent.getStringExtra("MODE") ?: "ADD"
        jumlah = intent.getIntExtra("JUMLAH", 1)

        getIntentData()
        initView()
        setupTitle()
        setupRecyclerView()
        setupClick()
        observeViewModel()
    }

    private fun getIntentData() {
        if (mode == "EDIT") {
            dataGrup = getParcelable("DATA_GRUP", GrupWithCustomer::class.java)
        } else {
            grupSementara = getParcelable("DATA_GRUP", GrupSementara::class.java)
        }
    }

    private fun <T> getParcelable(key: String, clazz: Class<T>): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(key, clazz)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(key) as? T
        }
    }

    private fun initView() {
        rvForm = findViewById(R.id.rvForm)
        btnSimpan = findViewById(R.id.btnSimpan)
        btnBack = findViewById(R.id.btnBack)
        tvTitle = findViewById(R.id.tvTitle)
    }

    private fun setupTitle() {
        tvTitle.text = if (mode == "EDIT") "Edit Data Pelanggan" else "Tambah Data Pelanggan"
    }

    private fun setupRecyclerView() {
        val listPelanggan = ArrayList<Pelanggan>()
        val listDetail = ArrayList<DetailLaundry>()

        // 1. Ambil data dari API/Intent jika EDIT
        if (mode == "EDIT" && dataGrup != null) {
            // Log untuk memastikan data dari server ada isinya
            Log.d("DEBUG_APP", "Data Pelanggan dari API: ${dataGrup?.pelanggan?.size}")
            Log.d("DEBUG_APP", "Data Detail dari API: ${dataGrup?.detail_laundry?.size}")

            listPelanggan.addAll(dataGrup!!.pelanggan)
            listDetail.addAll(dataGrup!!.detail_laundry)
        }

        // 2. SINKRONISASI TOTAL
        // Pastikan jumlah form sesuai dengan variabel 'jumlah'
        while (listPelanggan.size < jumlah) {
            listPelanggan.add(Pelanggan(nama_pelanggan = ""))
        }

        while (listDetail.size < jumlah) {
            listDetail.add(DetailLaundry(
                id = null,
                id_grup = dataGrup?.id_grup ?: 0,
                id_pelanggan = 0,
                baju = 0, rok = 0, jilbab = 0, kaos = 0,
                keterangan = ""
            ))
        }

        // Jika kelebihan (misal data di DB ada 5, tapi user input jumlah 2)
        if (listPelanggan.size > jumlah) {
            val p = listPelanggan.take(jumlah)
            listPelanggan.clear()
            listPelanggan.addAll(p)
        }
        if (listDetail.size > jumlah) {
            val d = listDetail.take(jumlah)
            listDetail.clear()
            listDetail.addAll(d)
        }

        val currentGrup = if (mode == "EDIT" && dataGrup != null) {
            GrupSementara(dataGrup!!.tanggal, dataGrup!!.jam, dataGrup!!.jenis_pakaian, dataGrup!!.kamar, dataGrup!!.berat, jumlah)
        } else {
            grupSementara ?: GrupSementara("", "", "", "", 0.0, jumlah)
        }

        // 3. Inisialisasi Adapter
        adapter = FormAdapter(currentGrup, listPelanggan, listDetail, dataGrup?.id_grup ?: 0)
        rvForm.layoutManager = LinearLayoutManager(this)
        rvForm.adapter = adapter

        // Tambahkan ini untuk memastikan UI refresh setelah data siap
        adapter.notifyDataSetChanged()
    }

    private fun setupClick() {
        btnBack.setOnClickListener { finish() }

        btnSimpan.setOnClickListener {
            currentFocus?.clearFocus()

            val updatedData = adapter.getUpdatedGrup()

            if (updatedData.pelanggan.any { it.nama_pelanggan.isBlank() }) {
                Toast.makeText(this, "Semua nama pelanggan harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tanggalFix = try {
                val inputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                outputFormat.format(inputFormat.parse(updatedData.tanggal)!!)
            } catch (e: Exception) {
                updatedData.tanggal
            }

            val request = updatedData.copy(
                id = if (mode == "EDIT") dataGrup?.id_grup else null,
                tanggal = tanggalFix
            )

            if (mode == "ADD") {
                viewModel.addGrup(request)
            } else {
                viewModel.updateGrup(dataGrup?.id_grup ?: 0, request)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.successMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }
}