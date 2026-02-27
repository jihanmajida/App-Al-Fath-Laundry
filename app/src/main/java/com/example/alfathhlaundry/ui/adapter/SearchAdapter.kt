package com.example.alfathhlaundry.ui.adapter

import GrupWithCustomer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
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
        private val btnShow: ImageButton = itemView.findViewById(R.id.btnShow)

        fun bind(data: GrupWithCustomer) {

            // Judul lebih informatif
            tvJudul.text = "${data.kamar} - ${data.tanggal}"

            // Gabungkan nama pelanggan
            val namaList = data.pelanggan
                ?.map { it.nama_pelanggan }
                ?.filter { !it.isNullOrEmpty() }
                ?.joinToString(", ")

            tvNama.text = if (namaList.isNullOrEmpty()) {
                "Tidak ada pelanggan"
            } else {
                namaList
            }

            btnShow.setOnClickListener {
                onClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
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

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return listGrup[oldItemPosition].id_grup ==
                        newList[newItemPosition].id_grup
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return listGrup[oldItemPosition] == newList[newItemPosition]
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listGrup = newList
        diffResult.dispatchUpdatesTo(this)
    }
}