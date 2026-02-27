package com.example.alfathhlaundry.ui.adapter

import GrupWithCustomer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.data.model.user.*

class FormAdapter(
    private val grupSementara: GrupSementara,
    private var pelangganList: ArrayList<Pelanggan>,
    private var detailList: ArrayList<DetailLaundry>,
    private val idGrupAsli: Int = 0 // Tambahan untuk menyimpan ID saat EDIT
) : RecyclerView.Adapter<FormAdapter.ViewHolder>() {

    // 1. Pastikan class ViewHolder didefinisikan dengan benar di sini
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etNama: EditText = itemView.findViewById(R.id.etNama)
        val etBaju: EditText = itemView.findViewById(R.id.etBaju)
        val etRok: EditText = itemView.findViewById(R.id.etRok)
        val etJilbab: EditText = itemView.findViewById(R.id.etJilbab)
        val etKaos: EditText = itemView.findViewById(R.id.etKaos)
        val etKeterangan: EditText = itemView.findViewById(R.id.etKeterangan)
    }

    // 2. Implementasi getItemCount
    override fun getItemCount(): Int = pelangganList.size

    // 3. Implementasi onCreateViewHolder (Error di gambar mengarah ke sini)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_add_data_customer, parent, false)
        return ViewHolder(view)
    }

    // 4. Implementasi onBindViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pelanggan = pelangganList[position]
        val detail = detailList.getOrNull(position)

        holder.etNama.clearFocus()

        //setdata ke view
        holder.etBaju.setText(detail?.baju?.toString() ?: "0")
        holder.etRok.setText(detail?.rok?.toString() ?: "0")
        holder.etJilbab.setText(detail?.jilbab?.toString() ?: "0")
        holder.etKaos.setText(detail?.kaos?.toString() ?: "0")
        holder.etKeterangan.setText(detail?.keterangan ?: "")

        holder.etNama.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) pelangganList[position].nama_pelanggan = (v as EditText).text.toString()
        }

        // Update detail laundry
        holder.etNama.addTextChangedListener { pelangganList[holder.adapterPosition].nama_pelanggan = it.toString() }
        holder.etBaju.addTextChangedListener { updateDetail(holder.adapterPosition) { baju = it.toString().toIntOrNull() ?: 0 } }
        holder.etRok.addTextChangedListener { updateDetail(holder.adapterPosition) { rok = it.toString().toIntOrNull() ?: 0 } }
        holder.etJilbab.addTextChangedListener { updateDetail(holder.adapterPosition) { jilbab = it.toString().toIntOrNull() ?: 0 } }
        holder.etKaos.addTextChangedListener { updateDetail(holder.adapterPosition) { kaos = it.toString().toIntOrNull() ?: 0 } }
        holder.etKeterangan.addTextChangedListener { updateDetail(holder.adapterPosition) { keterangan = it.toString() } }
    }

    private fun updateDetail(position: Int, action: DetailLaundry.() -> Unit) {
        detailList.getOrNull(position)?.let {
            it.action()
        }
    }

    fun getUpdatedGrup(): GrupWithCustomer {
        return GrupWithCustomer(
            id_grup = idGrupAsli, // Gunakan ID asli agar Laravel melakukan UPDATE bukan INSERT baru
            id_user = 0,
            tanggal = grupSementara.tanggal,
            jam = grupSementara.jam,
            kamar = grupSementara.kamar,
            berat = grupSementara.berat.toString().toDoubleOrNull()?:0.0,
            jenis_pakaian = grupSementara.jenisPakaian,
            jumlah_orang = grupSementara.jumlahOrang,
            status_data = "0",
            created_at = "",
            updated_at = "",
            pelanggan = pelangganList,
            detail_laundry = detailList
        )
    }
}