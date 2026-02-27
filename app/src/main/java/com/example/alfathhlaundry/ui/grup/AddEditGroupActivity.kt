package com.example.alfathhlaundry.ui.grup

import GrupWithCustomer
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.data.model.user.GrupSementara
import com.example.alfathhlaundry.ui.customer.AddEditDataCustomerActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddEditGroupActivity : AppCompatActivity() {

    private lateinit var etTanggal: EditText
    private lateinit var etJam: EditText
    private lateinit var etBerat: EditText
    private lateinit var etJumlah: EditText
    private lateinit var spSeragam: Spinner
    private lateinit var spKamar: Spinner
    private lateinit var tvTitle: TextView
    private lateinit var btnNext: Button
    private lateinit var btnBack: ImageButton
    private var dataItem: GrupWithCustomer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_group)

        initView()
        setupTitle()
        setupSpinner()
        setupData()
        setupClick()
        setupDateTime()
    }

    private fun initView() {
        etTanggal = findViewById(R.id.etTanggal)
        etJam = findViewById(R.id.etJam)
        etBerat = findViewById(R.id.etBerat)
        etJumlah = findViewById(R.id.etJumlah)
        spSeragam = findViewById(R.id.spSeragam)
        spKamar = findViewById(R.id.spKamar)
        tvTitle = findViewById(R.id.tvTitle)
        btnNext = findViewById(R.id.btnNext)
        btnBack = findViewById(R.id.btnBack)
    }

    private fun setupTitle() {
        val mode = intent.getStringExtra("MODE")

        if (mode == "EDIT") {
            tvTitle.text = "Edit Data Grup"
        } else {
            tvTitle.text = "Tambah Data Grup"
        }
    }

    private fun setupData() {
        val mode = intent.getStringExtra("MODE")
        if (mode == "EDIT") {
            // 1. Assign the intent data to the class variable dataItem
            dataItem = intent.getParcelableExtra<GrupWithCustomer>("EXTRA_DATA")

            if (dataItem == null) {
                Log.e("AddEditGroup", "GrupWithCustomer data is missing!")
                Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
                // Optional: finish() if you don't want to show an empty edit screen
                return
            }

            // 2. Use the data to fill the fields
            dataItem?.let {
                etTanggal.setText(it.tanggal)
                etJam.setText(it.jam)
                etBerat.setText(it.berat) // Removed .toString() because berat is already String in your model
                etJumlah.setText(it.jumlah_orang.toString())
                setSpinnerValue(spSeragam, it.jenis_pakaian)
                setSpinnerValue(spKamar, it.kamar)
            }
        }
    }

    private fun setSpinnerValue(spinner: Spinner, value: String){
        for(i in 0 until spinner.count){
            if(spinner.getItemAtPosition(i).toString() == value){
                spinner.setSelection(i)
                break
            }
        }
    }

    private fun setupClick() {

        btnBack.setOnClickListener {
            finish()
        }

        btnNext.setOnClickListener {
            if (!validateForm()) return@setOnClickListener

            // Use toDoubleOrNull() to prevent crashes on invalid input
            val berat = etBerat.text.toString().toDoubleOrNull() ?: 0.0
            val jumlah = etJumlah.text.toString().toIntOrNull() ?: 0

            val grup = GrupSementara(
                etTanggal.text.toString(),
                etJam.text.toString(),
                spSeragam.selectedItem.toString(),
                spKamar.selectedItem.toString(),
                berat,
                jumlah
            )

            val nextIntent = Intent(this, AddEditDataCustomerActivity::class.java)
            nextIntent.putExtra("DATA_GRUP", grup)

            // Pass the mode from the current activity to the next one
            val currentMode = intent.getStringExtra("MODE")
            nextIntent.putExtra("MODE", currentMode)

            // If you are in EDIT mode, you MUST also pass the ID or the whole object
            // so the next screen knows WHICH record to update in the database
            if (currentMode == "EDIT") {
                nextIntent.putExtra("EXTRA_DATA", dataItem)
            }

            startActivity(nextIntent)
        }
    }

    private fun setupSpinner() {

        // Spinner Seragam
        val listSeragam = listOf(
            "Pilih Seragam",
            "OSIS",
            "Olahraga",
            "Batik",
            "Kepanduan",
            "Pramuka",
            "Lainnya"
        )

        val adapterSeragam = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listSeragam
        )

        adapterSeragam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spSeragam.adapter = adapterSeragam

        // Spinner Kamar
        val listKamar = mutableListOf("Pilih Kamar")

        for (i in 1..14) {
            listKamar.add("Alexandria $i")
        }

        val adapterKamar = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listKamar
        )

        adapterKamar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spKamar.adapter = adapterKamar
    }

    private fun setupDateTime() {

        val calendar = Calendar.getInstance()

        // Set otomatis saat buka
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        etTanggal.setText(dateFormat.format(calendar.time))
        etJam.setText(timeFormat.format(calendar.time))

        // Klik Tanggal
        etTanggal.setOnClickListener {

            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->

                    val selectedCalendar = Calendar.getInstance()
                    selectedCalendar.set(year, month, dayOfMonth)

                    etTanggal.setText(
                        dateFormat.format(selectedCalendar.time)
                    )
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }

        // Klik Jam
        etJam.setOnClickListener {

            val timePicker = TimePickerDialog(
                this,
                { _, hourOfDay, minute ->

                    val selectedCalendar = Calendar.getInstance()
                    selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedCalendar.set(Calendar.MINUTE, minute)

                    etJam.setText(
                        timeFormat.format(selectedCalendar.time)
                    )
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )

            timePicker.show()
        }
    }

    private fun validateForm(): Boolean {

        if (etTanggal.text.isEmpty() ||
            etJam.text.isEmpty() ||
            etBerat.text.isEmpty() ||
            etJumlah.text.isEmpty()
        ) {
            Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
            return false
        }

        if (spSeragam.selectedItemPosition == 0) {
            Toast.makeText(this, "Pilih jenis seragam", Toast.LENGTH_SHORT).show()
            return false
        }

        if (spKamar.selectedItemPosition == 0) {
            Toast.makeText(this, "Pilih kamar", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}