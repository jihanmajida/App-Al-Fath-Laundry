package com.example.alfathhlaundry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.model.ItemListData
import com.example.alfathhlaundry.R

class ListDataAdapter (
    private val listData: List<ItemListData>,
    private val onEditClick: (ItemListData) -> Unit,
    private val onDeleteClick: (ItemListData) -> Unit,
    private val onStatusChange: (ItemListData, Boolean) -> Unit
) : RecyclerView.Adapter<ListDataAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvBerat: TextView = itemView.findViewById(R.id.tvBerat)
        val tvJudul: TextView = itemView.findViewById(R.id.tvJudul)
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
        val cbStatus: CheckBox = itemView.findViewById(R.id.cbStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fragment_list_data, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listData[position]

        holder.tvBerat.text = item.berat
        holder.tvJudul.text = item.judul
        holder.tvNama.text = item.nama
        holder.cbStatus.isChecked = item.status

        holder.btnEdit.setOnClickListener {
            onEditClick(item)
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClick(item)
        }

        holder.cbStatus.setOnCheckedChangeListener { _, isChecked ->
            onStatusChange(item, isChecked)
        }
    }
}