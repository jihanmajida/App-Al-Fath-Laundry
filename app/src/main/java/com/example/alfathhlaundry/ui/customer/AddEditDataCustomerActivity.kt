package com.example.alfathhlaundry.ui.customer

import AddGrupRequest
import DetailLaundryRequest
import GrupWithCustomer
import PelangganRequest
import android.content.Intent
import android.os.Build
import android.os.Bundle
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
import java.io.Serializable
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

    // untuk ADD
    private var grupSementara: GrupSementara? = null

    // untuk EDIT
    private var dataGrup: GrupWithCustomer? = null

    private val viewModel: GrupViewModel by viewModels {
        GrupViewModelFactory(
            GrupRepository(RetrofitClient.getInstance(this))
        )
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

    // =========================
    // 🔥 AMBIL DATA INTENT
    // =========================

    private fun getIntentData() {
        if (mode == "EDIT") {
            dataGrup = getParcelable("DATA_GRUP")
        } else {
            grupSementara = getParcelable("DATA_GRUP")
        }
    }


    private fun <T> getParcelable(key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(key, GrupSementara::class.java) as? T
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
        tvTitle.text =
            if (mode == "EDIT") "Edit Data Pelangggan"
            else "Tambah Data Pelangggan"
    }

    // =========================
    // SETUP RECYCLER VIEW
    // =========================

    private fun setupRecyclerView() {
        val currentGrup = if (mode == "EDIT") dataGrup else grupSementara
        if (currentGrup == null) return

        // Ambil jumlah orang & data awal
        val totalOrang = if (dataGrup != null) dataGrup!!.jumlah_orang else grupSementara!!.jumlahOrang
        val listPelanggan = ArrayList<Pelanggan>()
        val listDetail = ArrayList<DetailLaundry>()

        if (mode == "EDIT" && dataGrup != null) {
            listPelanggan.addAll(dataGrup!!.pelanggan)
            listDetail.addAll(dataGrup!!.detailLaundry)
        } else {
            repeat(totalOrang) {
                listPelanggan.add(Pelanggan(0, ""))
                listDetail.add(DetailLaundry(null, 0, 0, 0, 0, 0, 0, ""))
            }
        }

        // Pastikan grupSementara tidak null untuk dikirim ke adapter
        val tmpGrup = if (mode == "EDIT" && dataGrup != null) {
            GrupSementara(
                dataGrup!!.tanggal, dataGrup!!.jam, dataGrup!!.jenis_pakaian,
                dataGrup!!.kamar, dataGrup!!.berat.toDoubleOrNull() ?: 0.0, dataGrup!!.jumlah_orang
            )
        } else {
            grupSementara!!
        }

        // Masukkan ID Grup asli jika mode EDIT
        val idAsli = if (mode == "EDIT") dataGrup?.id_grup ?: 0 else 0

        adapter = FormAdapter(tmpGrup, listPelanggan, listDetail, idAsli)
        rvForm.layoutManager = LinearLayoutManager(this)
        rvForm.adapter = adapter
    }

    // BUTTON
    private fun setupClick() {

        btnBack.setOnClickListener { finish() }

        btnSimpan.setOnClickListener {

            val data = adapter.getUpdatedGrup()

            if (data.pelanggan.any { it.nama_pelanggan.isBlank() }) {
                Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {

                val inputDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                val tanggalFix = outputDateFormat.format(
                    inputDateFormat.parse(data.tanggal)!!
                )

                val request = AddGrupRequest(
                    tanggal = tanggalFix,
                    jam = data.jam,
                    kamar = data.kamar,
                    berat = data.berat,
                    jenisPakaian = data.jenis_pakaian,
                    jumlahOrang = data.jumlah_orang,
                    statusData = "0",

                    // 🔥 CONVERT KE REQUEST
                    pelanggan = data.pelanggan.map {
                        PelangganRequest(
                            nama_pelanggan = it.nama_pelanggan
                        )
                    },

                    detail_laundry = data.detailLaundry.map {
                        DetailLaundryRequest(
                            baju = it.baju,
                            jilbab = it.jilbab,
                            rok = it.rok,
                            kaos = it.kaos,
                            keterangan = it.keterangan
                        )
                    }
                )

                if (mode == "ADD") {
                    viewModel.addGrup(request)
                } else {
                    viewModel.updateGrup(data.id_grup, request)
                }

            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Format tanggal salah",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun observeViewModel() {

        viewModel.successMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }
}