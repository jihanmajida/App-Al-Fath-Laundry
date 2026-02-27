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
        val currentGrup = if (mode == "EDIT") dataGrup else grupSementara
        if (currentGrup == null) return

        val listPelanggan = ArrayList<Pelanggan>()
        val listDetail = ArrayList<DetailLaundry>()

        if (mode == "EDIT" && dataGrup != null) {
            listPelanggan.addAll(dataGrup!!.pelanggan)
            listDetail.addAll(dataGrup!!.detail_laundry)
        } else {
            repeat(jumlah) {
                listPelanggan.add(Pelanggan(0, ""))
                listDetail.add(DetailLaundry(null, 0, 0, 0, 0, 0, 0, ""))
            }
        }

        val tmpGrup = if (mode == "EDIT" && dataGrup != null) {
            GrupSementara(
                dataGrup!!.tanggal, dataGrup!!.jam, dataGrup!!.jenis_pakaian,
                dataGrup!!.kamar, dataGrup!!.berat.toString().toDoubleOrNull() ?: 0.0, dataGrup!!.jumlah_orang
            )
        } else {
            grupSementara!!
        }

        val idAsli = if (mode == "EDIT") dataGrup?.id_grup ?: 0 else 0
        adapter = FormAdapter(tmpGrup, listPelanggan, listDetail, idAsli)
        rvForm.layoutManager = LinearLayoutManager(this)
        rvForm.adapter = adapter
    }

    private fun setupClick() {
        btnBack.setOnClickListener { finish() }

        btnSimpan.setOnClickListener {
            // Paksa simpan data dari EditText yang sedang fokus
            currentFocus?.clearFocus()

            val data = adapter.getUpdatedGrup()
            Log.d("API_SEND", "Pelanggan: ${data.pelanggan.size}, Detail: ${data.detail_laundry.size}")

            if (data.pelanggan.any { it.nama_pelanggan.isBlank() }) {
                Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                // Konversi tanggal ke format database (yyyy-MM-dd)
                val tanggalFix = try {
                    val inputDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
                    val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    outputDateFormat.format(inputDateFormat.parse(data.tanggal)!!)
                } catch (e: Exception) {
                    data.tanggal // Gunakan apa adanya jika sudah benar
                }

                val request = AddGrupRequest(
                    tanggal = tanggalFix,
                    jam = data.jam,
                    kamar = data.kamar,
                    berat = data.berat.toString().toDoubleOrNull()?:0.0,
                    jenisPakaian = data.jenis_pakaian,
                    jumlahOrang = data.jumlah_orang,
                    statusData = "0",
                    pelanggan = data.pelanggan.map { PelangganRequest(it.nama_pelanggan) },
                    detail_laundry = data.detail_laundry.map {
                        DetailLaundryRequest(it.baju, it.jilbab, it.rok, it.kaos, it.keterangan)
                    }
                )

                Log.d("API_REQUEST", request.toString())

                if (mode == "ADD") viewModel.addGrup(request)
                else viewModel.updateGrup(data.id_grup, request)

            } catch (e: Exception) {
                Log.e("API_ERROR", "Error: ${e.message}")
                Toast.makeText(this, "Terjadi kesalahan data", Toast.LENGTH_LONG).show()
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