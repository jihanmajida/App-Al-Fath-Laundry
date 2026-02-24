package com.example.alfathhlaundry.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.model.GrupData
import com.example.alfathhlaundry.model.GrupWithCustomer
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
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

    private fun setupData(){
        val mode = intent.getStringExtra("MODE")
        if(mode == "EDIT"){
            dataItem =
                intent.getSerializableExtra("DATA_GRUP") as GrupWithCustomer
            dataItem?.let {
                etTanggal.setText(it.grup.tanggal)
                etJam.setText(it.grup.jam)
                etBerat.setText(it.grup.berat.toString())
                etJumlah.setText(it.grup.jumlahOrang.toString())
                setSpinnerValue(spSeragam, it.grup.seragam)
                setSpinnerValue(spKamar, it.grup.kamar)

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

            val grup = GrupData(
                etTanggal.text.toString(),
                etJam.text.toString(),
                spSeragam.selectedItem.toString(),
                spKamar.selectedItem.toString(),
                etBerat.text.toString().toDouble(),
                etJumlah.text.toString().toInt()
            )

            val intent = Intent(this, AddEditDataCustomerActivity::class.java)
            intent.putExtra("GRUP_OBJECT", grup)
            if (dataItem != null) {
                intent.putExtra("MODE", "EDIT")
                intent.putExtra("DATA_GRUP", dataItem)
            } else {
                intent.putExtra("MODE", "ADD")
            }
            intent.putExtra("JUMLAH", grup.jumlahOrang)
            startActivity(intent)
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