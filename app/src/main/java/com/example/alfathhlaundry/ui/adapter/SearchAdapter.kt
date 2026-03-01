package com.example.alfathhlaundry.ui.adapter

import GrupWithCustomer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.R


class SearchAdapter(
    private val onClick: (GrupWithCustomer) -> Unit
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var listGrup: List<GrupWithCustomer> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvJudul: TextView = itemView.findViewById(R.id.tvJudul)
        private val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        private val btnShow: ImageView = itemView.findViewById(R.id.btnShow) // Sesuai tipe XML

        fun bind(data: GrupWithCustomer) {
            tvJudul.text = "${data.kamar} - ${data.tanggal}"

            val namaList = data.pelanggan
                ?.map { it.nama_pelanggan }
                ?.filter { !it.isNullOrEmpty() }
                ?.joinToString(", ")

            tvNama.text = if (namaList.isNullOrEmpty()) "Tidak ada pelanggan" else namaList

            // Klik pada icon
            btnShow.setOnClickListener { onClick(data) }

            // Klik pada seluruh kartu agar user tidak susah membidik icon
            itemView.setOnClickListener { onClick(data) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Pastikan nama file XML item-nya benar
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_search_data, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listGrup.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listGrup[position])
    }

    fun updateData(newList: List<GrupWithCustomer>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = listGrup.size
            override fun getNewListSize() = newList.size
            override fun areItemsTheSame(oldPos: Int, newPos: Int) =
                listGrup[oldPos].id_grup == newList[newPos].id_grup
            override fun areContentsTheSame(oldPos: Int, newPos: Int) =
                listGrup[oldPos] == newList[newPos]
        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listGrup = newList
        diffResult.dispatchUpdatesTo(this)
    }
}