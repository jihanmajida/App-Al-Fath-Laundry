package com.example.alfathhlaundry.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.data.network.RetrofitClient
import com.example.alfathhlaundry.data.repository.GrupRepository
import com.example.alfathhlaundry.ui.grup.AddEditGroupActivity
import com.example.alfathhlaundry.ui.home.fragment.EmptyFragment
import com.example.alfathhlaundry.ui.home.fragment.ListDataFragment
import com.example.alfathhlaundry.ui.login.LoginActivity
import com.example.alfathhlaundry.ui.search.SearchActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var tvHari: TextView
    private lateinit var tvTanggal: TextView
    private lateinit var btnPrev: ImageButton
    private lateinit var btnNext: ImageButton
    private lateinit var btnSearch: ImageButton
    private lateinit var btnLogout: ImageButton
    private lateinit var fabTambah: FloatingActionButton

    private var currentDate: LocalDate = LocalDate.now()

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(
            GrupRepository(RetrofitClient.getInstance(this))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initView()
        observeData()
        updateDateAndLoad()

        // PREV BUTTON
        btnPrev.setOnClickListener {
            currentDate = currentDate.minusDays(1)
            updateDateAndLoad()
        }

        // NEXT BUTTON
        btnNext.setOnClickListener {
            currentDate = currentDate.plusDays(1)
            updateDateAndLoad()
        }

        // SEARCH
        btnSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        // TAMBAH DATA
        fabTambah.setOnClickListener {
            val intent = Intent(this, AddEditGroupActivity::class.java)
            intent.putExtra("MODE", "ADD")
            startActivity(intent)
        }

        // LOGOUT
        btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    override fun onResume() {
        super.onResume()
        updateDateAndLoad()
    }

    // INIT VIEW
    private fun initView() {
        tvHari = findViewById(R.id.tvHari)
        tvTanggal = findViewById(R.id.tvTanggal)
        btnPrev = findViewById(R.id.btnPrev)
        btnNext = findViewById(R.id.btnNext)
        btnSearch = findViewById(R.id.btnSearch)
        btnLogout = findViewById(R.id.btnLogOut)
        fabTambah = findViewById(R.id.fabTambah)
    }

    // OBSERVE DATA FROM VIEWMODEL
    private fun observeData() {
        viewModel.grupData.observe(this) { listGrup ->
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

            val newFragment = if (listGrup.isEmpty()) EmptyFragment() else ListDataFragment()

            // Hanya ganti fragment jika tipe fragment berbeda dari yang sekarang
            if (currentFragment == null || currentFragment::class != newFragment::class) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, newFragment)
                    .commit()
            }
        }
    }

    // UPDATE TANGGAL & LOAD DATA
    private fun updateDateAndLoad() {

        val localeID = Locale("id", "ID")

        val hariFormatter =
            DateTimeFormatter.ofPattern("EEEE", localeID)
        val tanggalFormatter =
            DateTimeFormatter.ofPattern("dd MMMM yyyy", localeID)
        val apiFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd")

        tvHari.text = currentDate.format(hariFormatter)
        tvTanggal.text = currentDate.format(tanggalFormatter)

        viewModel.loadDataByDate(
            currentDate.format(apiFormatter)
        )
    }

    // LOGOUT DIALOG
    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->

                getSharedPreferences("SESSION", MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply()

                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }
}