package com.example.alfathhlaundry.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.alfathhlaundry.DataStorage.DataStorage
import com.example.alfathhlaundry.fragment.ListDataFragment
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.fragment.EmptyFragment
import com.example.alfathhlaundry.model.ItemListData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.time.format.TextStyle
import java.util.Locale
import java.time.LocalDate
import java.util.Calendar

class HomeActivity : AppCompatActivity() {

    private lateinit var tvHari: TextView
    private lateinit var tvTanggal: TextView
    private lateinit var btnPrev: ImageButton
    private lateinit var btnNext: ImageButton
    private lateinit var btnSearch: ImageButton
    private lateinit var btnLogout: ImageButton
    private lateinit var fabTambah: FloatingActionButton

    private var currentDate: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initView()
        updateDate()
        loadFragmentByDate()

        btnPrev.setOnClickListener {
            currentDate.add(Calendar.DAY_OF_MONTH, -1)
            updateDate()
            loadFragmentByDate()
        }

        btnNext.setOnClickListener {
            currentDate.add(Calendar.DAY_OF_MONTH, 1)
            updateDate()
            loadFragmentByDate()
        }

        btnSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        btnLogout.setOnClickListener {
            showLogoutDialog()
        }

        fabTambah.setOnClickListener {
            val intent = Intent(this, AddEditGroupActivity::class.java)
            intent.putExtra("MODE", "ADD")
            startActivity(intent)
        }
    }

    private fun initView() {
        tvHari = findViewById(R.id.tvHari)
        tvTanggal = findViewById(R.id.tvTanggal)
        btnPrev = findViewById(R.id.btnPrev)
        btnNext = findViewById(R.id.btnNext)
        btnSearch = findViewById(R.id.btnSearch)
        btnLogout = findViewById(R.id.btnLogOut)
        fabTambah = findViewById(R.id.fabTambah)
    }

    private fun updateDate() {
        val formatHari = SimpleDateFormat("EEEE", Locale("id", "ID"))
        val formatTanggal = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))

        tvHari.text = formatHari.format(currentDate.time)
        tvTanggal.text = formatTanggal.format(currentDate.time)
    }

    override fun onResume() {
        super.onResume()
        loadFragmentByDate()
    }

    private fun loadFragmentByDate() {

        // Ambil semua grup dari DataStorage
        val data = DataStorage.listGrup

        if (data.isEmpty()) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, EmptyFragment())
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ListDataFragment())
                .commit()
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }
}