package com.example.alfathhlaundry.adapter

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.model.ItemListData
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.activity.AddEditGroupActivity
import com.example.alfathhlaundry.activity.ShowDataActivity

class ListDataAdapter (
    private val listData: MutableList<ItemListData>,
    private val onItemClick: (ItemListData) -> Unit,
    private val onEditClick: (ItemListData) -> Unit,
    private val onDeleteClick: (ItemListData) -> Unit,
    private val onStatusChange: (ItemListData, Boolean) -> Unit
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

        holder.tvJudul.text = item.judul
        holder.tvNama.text = item.nama
        holder.tvBerat.text = item.berat
        holder.cbStatus.isChecked = item.status

        // Klik seluruh item
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }

        // Klik edit
        holder.btnEdit.setOnClickListener {
            onEditClick(item)
        }

        // Klik delete
        holder.btnDelete.setOnClickListener {
            onDeleteClick(item)
        }

        // Klik checkbox
        holder.cbStatus.setOnCheckedChangeListener { _, isChecked ->
            onStatusChange(item, isChecked)
        }
    }
}