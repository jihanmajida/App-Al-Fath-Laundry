package com.example.alfathhlaundry.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.data.model.user.Pelanggan

class ShowDataAdapter(
    private var list: List<Pelanggan>
) : RecyclerView.Adapter<ShowDataAdapter.ViewHolder>() {

    fun updateData(newList: List<Pelanggan>) {
        this.list = newList
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // ID ini harus sesuai dengan item_rv_show_data.xml
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

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.tvNama.text = item.nama_pelanggan
        holder.tvBaju.text = (item.baju ?: 0).toString()
        holder.tvRok.text = (item.rok ?: 0).toString()
        holder.tvJilbab.text = (item.jilbab ?: 0).toString()
        holder.tvKaos.text = (item.kaos ?: 0).toString()

        holder.tvKeterangan.text = if (item.keterangan.isNullOrEmpty()) "-" else item.keterangan
    }
}