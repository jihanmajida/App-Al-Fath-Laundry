package com.example.alfathhlaundry.ui.adapter

import GrupWithCustomer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.R

class ListDataAdapter(
    private var listData: List<GrupWithCustomer>,
    private val onItemClick: (GrupWithCustomer) -> Unit,
    private val onEditClick: (GrupWithCustomer) -> Unit,
    private val onDeleteClick: (GrupWithCustomer) -> Unit,
    private val onStatusChange: (GrupWithCustomer, Boolean) -> Unit
) : RecyclerView.Adapter<ListDataAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvJudul: TextView = view.findViewById(R.id.tvJudul)
        val tvNama: TextView = view.findViewById(R.id.tvNama)
        val tvBerat: TextView = view.findViewById(R.id.tvBerat)
        val btnEdit: ImageButton = view.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
        val cbStatus: CheckBox = view.findViewById(R.id.cbStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fragment_list_data, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listData[position]

        holder.tvJudul.text = item.kamar
        holder.tvNama.text = item.pelanggan.firstOrNull()?.nama_pelanggan ?: "-"
        holder.tvBerat.text = "${item.berat} Kg"

        holder.itemView.setOnClickListener { onItemClick(item) }
        holder.btnEdit.setOnClickListener { onEditClick(item) }
        holder.btnDelete.setOnClickListener { onDeleteClick(item) }

        // ✅ Checkbox aman (unregister listener dulu)
        holder.cbStatus.setOnCheckedChangeListener(null)
        holder.cbStatus.isChecked = item.status_data == "1"
        holder.cbStatus.setOnCheckedChangeListener { _, isChecked ->
            onStatusChange(item, isChecked)
        }
    }

    fun updateData(newData: List<GrupWithCustomer>) {
        this.listData = newData // Pastikan ini listData yang dipakai di getItemCount
        notifyDataSetChanged() // Memaksa RecyclerView menggambar ulang
    }
}