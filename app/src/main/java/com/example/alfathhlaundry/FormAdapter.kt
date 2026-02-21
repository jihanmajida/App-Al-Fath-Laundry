package com.example.alfathhlaundry

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

class FormAdapter : RecyclerView.Adapter<FormAdapter.FormViewHolder>() {

    var etNama: EditText? = null
    var etBaju: EditText? = null
    var etRok: EditText? = null
    var etJilbab: EditText? = null
    var etKaos: EditText? = null
    var etKeterangan: EditText? = null

    inner class FormViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            etNama = view.findViewById(R.id.etNama)
            etBaju = view.findViewById(R.id.etBaju)
            etRok = view.findViewById(R.id.etRok)
            etJilbab = view.findViewById(R.id.etJilbab)
            etKaos = view.findViewById(R.id.etKaos)
            etKeterangan = view.findViewById(R.id.etKeterangan)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_add_data_customer, parent, false)
        return FormViewHolder(view)
    }

    override fun getItemCount(): Int = 1   // cuma 1 form

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {}

    fun getData(): FormData {
        return FormData(
            nama = etNama?.text.toString(),
            baju = etBaju?.text.toString().toIntOrNull() ?: 0,
            rok = etRok?.text.toString().toIntOrNull() ?: 0,
            jilbab = etJilbab?.text.toString().toIntOrNull() ?: 0,
            kaos = etKaos?.text.toString().toIntOrNull() ?: 0,
            keterangan = etKeterangan?.text.toString()
        )
    }

    fun setData(data: FormData) {
        etNama?.setText(data.nama)
        etBaju?.setText(data.baju.toString())
        etRok?.setText(data.rok.toString())
        etJilbab?.setText(data.jilbab.toString())
        etKaos?.setText(data.kaos.toString())
        etKeterangan?.setText(data.keterangan)
    }
}