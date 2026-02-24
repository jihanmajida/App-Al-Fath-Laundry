package com.example.alfathhlaundry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.model.SearchData

class SearchAdapter(
    private var listGrup: List<SearchData>,
    private val onClick: (SearchData) -> Unit
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvJudul: TextView = itemView.findViewById(R.id.tvJudul)
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val btnShow: ImageButton = itemView.findViewById(R.id.btnShow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_search_data, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listGrup.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val grup = listGrup[position]

        holder.tvJudul.text = grup.namaKamar
        holder.tvNama.text = grup.listNamaPelanggan.joinToString(", ")

        holder.btnShow.setOnClickListener {
            onClick(grup)
        }
    }

    fun updateData(newList: List<SearchData>) {
        listGrup = newList
        notifyDataSetChanged()
    }
}