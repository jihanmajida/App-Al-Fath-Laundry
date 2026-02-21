package com.example.alfathhlaundry

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddEditGroupActivity : AppCompatActivity() {

    private lateinit var etTanggal: EditText
    private lateinit var etJam: EditText
    private lateinit var etJumlah: EditText
    private lateinit var spSeragam: Spinner
    private lateinit var spKamar: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_group)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnNext = findViewById<Button>(R.id.btnNext)

        etTanggal = findViewById(R.id.etTanggal)
        etJam = findViewById(R.id.etJam)
        etJumlah = findViewById(R.id.etJumlah)
        spSeragam = findViewById(R.id.spSeragam)
        spKamar = findViewById(R.id.spKamar)

        // BACK -> HOME
        btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        setupTimeNow()
        setupDatePicker()
        setupSpinner()

        // NEXT -> Add Data Customer
        btnNext.setOnClickListener {
            val jumlah = etJumlah.text.toString().toIntOrNull()

            if (jumlah == null || jumlah <= 0) {
                Toast.makeText(this, "Masukkan jumlah orang yang valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, AddEditDataCustomerActivity::class.java)
            intent.putExtra("JUMLAH", jumlah)
            startActivity(intent)
        }
    }

    // ================= HELPER =================
    private fun setupTimeNow() {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        etJam.setText(sdf.format(Date()))
        etJam.isEnabled = false
    }

    private fun setupDatePicker() {
        etTanggal.isFocusable = false
        etTanggal.setOnClickListener {

            val cal = Calendar.getInstance()

            DatePickerDialog(
                this,
                { _, y, m, d ->
                    val date = String.format("%02d-%02d-%d", d, m + 1, y)
                    etTanggal.setText(date)
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupSpinner() {

        val seragamList = listOf(
            "OSIS",
            "Batik",
            "Pramuka",
            "Olahraga",
            "Kepanduan",
            "Lainnya"
        )

        val kamarList = listOf(
            "Kamar Alexandria 1", "Kamar Alexandria 2", "Kamar Alexandria 3", "Kamar Alexandria 4", "Kamar Alexandria 5",
            "Kamar Alexandria 6", "Kamar Alexandria 7", "Kamar Alexandria 8", "Kamar Alexandria 9", "Kamar Alexandria 10",
            "Kamar Alexandria 11", "Kamar Alexandria 12", "Kamar Alexandria 31", "Kamar Alexandria 14"
        )

        val seragamAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            seragamList
        )

        seragamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spSeragam.adapter = seragamAdapter

        val kamarAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            kamarList
        )
        kamarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spKamar.adapter = kamarAdapter
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}