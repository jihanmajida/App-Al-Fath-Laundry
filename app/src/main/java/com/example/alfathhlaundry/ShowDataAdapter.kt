package com.example.alfathhlaundry

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShowDataAdapter (private val dataList: List<ModelData>) :
RecyclerView.Adapter<ShowDataAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvWaktu: TextView = itemView.findViewById(R.id.tvWaktu)
        val tvJenis: TextView = itemView.findViewById(R.id.tvJenis)
        val tvBerat: TextView = itemView.findViewById(R.id.tvBerat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rv_show_data, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.tvNama.text = item.nama
        holder.tvWaktu.text = item.waktu
        holder.tvJenis.text = item.jenisSeragam
        holder.tvBerat.text = item.berat
    }
}