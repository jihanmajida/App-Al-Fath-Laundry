package com.example.alfathhlaundry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.model.ShowData
import com.example.alfathhlaundry.R

class ShowDataAdapter (private val list: List<ShowData>) :
RecyclerView.Adapter<ShowDataAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvBaju: TextView = itemView.findViewById(R.id.tvBaju)
        val tvRok: TextView = itemView.findViewById(R.id.tvRok)
        val tvJilbab: TextView = itemView.findViewById(R.id.tvJilbab)
        val tvKaos: TextView = itemView.findViewById(R.id.tvKaos)
        val tvKeterangan: TextView = itemView.findViewById(R.id.tvKeterangan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_show_data, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]

        holder.tvNama.text = data.nama
        holder.tvBaju.text = data.baju.toString()
        holder.tvRok.text = data.rok.toString()
        holder.tvJilbab.text = data.jilbab.toString()
        holder.tvKaos.text = data.kaos.toString()
        holder.tvKeterangan.text = data.keterangan
    }
}