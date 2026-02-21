package com.example.alfathhlaundry

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.format.TextStyle
import java.util.Locale
import java.time.LocalDate

class HomeActivity : AppCompatActivity() {

    private lateinit var tvHari:TextView
    private lateinit var tvTanggal: TextView

    private lateinit var btnPrev: ImageButton
    private lateinit var btnNext: ImageButton
    private lateinit var btnSearch: ImageButton
    private lateinit var btnLogout: ImageButton
    private lateinit var fabTambah: FloatingActionButton


    private var currentDate: LocalDate = LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // ===== INIT VIEW =====
        tvHari = findViewById(R.id.tvHari)
        tvTanggal = findViewById(R.id.tvTanggal)

        btnPrev = findViewById(R.id.btnPrev)
        btnNext = findViewById(R.id.btnNext)
        btnSearch = findViewById(R.id.btnSearch)
        btnLogout = findViewById(R.id.btnLogOut)
        fabTambah = findViewById(R.id.fabTambah)

        // ===== SET TANGGAL AWAL =====
        updateTanggalUI()
        loadDataByDate()

        // ===== EVENT PREV / NEXT =====
        btnPrev.setOnClickListener {
            currentDate = currentDate.minusDays(1)
            updateTanggalUI()
            loadDataByDate()
        }

        btnNext.setOnClickListener {
            currentDate = currentDate.plusDays(1)
            updateTanggalUI()
            loadDataByDate()
        }

        // ===== SEARCH =====
        btnSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        // ===== LOGOUT =====
        btnLogout.setOnClickListener {
            showLogoutDialog()
        }
        // ===== FAB =====
        fabTambah.setOnClickListener {
            startActivity(Intent(this, AddEditGroupActivity::class.java))
        }
    }

    // UPDATE TANGGAL KE UI
    private fun updateTanggalUI() {
        val localeID = Locale("id", "ID")

        val hari = currentDate.dayOfWeek
            .getDisplayName(TextStyle.FULL, localeID)
            .replaceFirstChar { it.uppercase() }

        val tanggal = "${currentDate.dayOfMonth} " +
                currentDate.month.getDisplayName(TextStyle.FULL, localeID) +
                " ${currentDate.year}"
        tvHari.text = hari
        tvTanggal.text = tanggal
    }

    // LOAD DATA BERDASARKAN TANGGAL
    private fun loadDataByDate() {
        // TODO:
        // ambil data dari database / API berdasarkan currentDate
        // contoh sementara:
        val hasData = false
        showFragment(hasData)
    }

    // GANTI FRAGMENT
    private fun showFragment(hasData: Boolean) {
        val fragment = if (hasData) {
            ListDataFragment()
        } else {
            EmptyFragment()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    // DIALOG LOGOUT
    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setMessage("Apakah anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                finish()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }
}