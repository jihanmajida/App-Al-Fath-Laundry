package com.example.alfathhlaundry.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.data.model.user.CustomerData
import com.example.alfathhlaundry.data.model.user.Pelanggan

class ShowDataAdapter(
    private var list: List<Pelanggan>
) : RecyclerView.Adapter<ShowDataAdapter.ViewHolder>() {

    fun updateData(newList: List<Pelanggan>) {
        this.list = newList
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvJudul: TextView = view.findViewById(R.id.tvJudul)
        val tvNama: TextView = view.findViewById(R.id.tvNama)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_show_data, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.tvJudul.text = "Pelanggan ${position + 1}"
        holder.tvNama.text = item.nama_pelanggan // Pastikan CustomerData punya @SerializedName("nama_pelanggan")
    }
}