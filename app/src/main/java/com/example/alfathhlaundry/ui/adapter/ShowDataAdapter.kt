package com.example.alfathhlaundry.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.data.model.user.DetailLaundry
import com.example.alfathhlaundry.data.model.user.Pelanggan

class ShowDataAdapter(
    private var detailList: List<DetailLaundry>,
    private var pelangganList: List<Pelanggan>
) : RecyclerView.Adapter<ShowDataAdapter.ViewHolder>() {

    // Fungsi untuk memperbarui data dari Activity
    fun updateData(newDetails: List<DetailLaundry>, newPelanggans: List<Pelanggan>) {
        this.detailList = newDetails
        this.pelangganList = newPelanggans
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tvNama)
        val tvBaju: TextView = view.findViewById(R.id.tvBaju)
        val tvRok: TextView = view.findViewById(R.id.tvRok)
        val tvJilbab: TextView = view.findViewById(R.id.tvJilbab)
        val tvKaos: TextView = view.findViewById(R.id.tvKaos)
        val tvKeterangan: TextView = view.findViewById(R.id.tvKeterangan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_show_data, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = detailList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Sumber utama data adalah detailList (angka-angka)
        val detail = detailList[position]

        // Cari Nama Pelanggan yang ID-nya cocok dengan id_pelanggan di tabel detail_laundry
        // Jika tidak ketemu, gunakan index position sebagai fallback (asumsi urutan sama)
        val pelanggan = pelangganList.find { it.id_pelanggan == detail.id_pelanggan }
            ?: pelangganList.getOrNull(position)

        // Set Nama
        holder.tvNama.text = pelanggan?.nama_pelanggan ?: "Pelanggan ${position + 1}"

        // Set Angka (Sekarang ambil dari objek 'detail', bukan 'pelanggan')
        holder.tvBaju.text = detail.baju.toString()
        holder.tvRok.text = detail.rok.toString()
        holder.tvJilbab.text = detail.jilbab.toString()
        holder.tvKaos.text = detail.kaos.toString()

        holder.tvKeterangan.text = if (detail.keterangan.isNullOrEmpty()) "-" else detail.keterangan
    }
}