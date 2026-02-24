package com.example.alfathhlaundry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.model.CustomerData

class FormAdapter(
    private val jumlah: Int,
    private val existingData: List<CustomerData>? = null
) : RecyclerView.Adapter<FormAdapter.ViewHolder>() {

    private val listData = MutableList(jumlah) { index ->
        if (existingData != null && index < existingData.size) {
            existingData[index].copy()
        } else {
            CustomerData("", 0, 0, 0, 0, "")
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etNama: EditText = itemView.findViewById(R.id.etNama)
        val etBaju: EditText = itemView.findViewById(R.id.etBaju)
        val etRok: EditText = itemView.findViewById(R.id.etRok)
        val etJilbab: EditText = itemView.findViewById(R.id.etJilbab)
        val etKaos: EditText = itemView.findViewById(R.id.etKaos)
        val etKeterangan: EditText = itemView.findViewById(R.id.etKeterangan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_add_data_customer, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = jumlah

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]

        // SET DATA LAMA KE FORM
        holder.etNama.setText(data.nama)
        holder.etBaju.setText(data.baju.toString())
        holder.etRok.setText(data.rok.toString())
        holder.etJilbab.setText(data.jilbab.toString())
        holder.etKaos.setText(data.kaos.toString())
        holder.etKeterangan.setText(data.keterangan)

        // LISTENER UPDATE DATA
        holder.etNama.addTextChangedListener {
            listData[position].nama = it.toString()
        }

        holder.etBaju.addTextChangedListener {
            listData[position].baju =
                it.toString().toIntOrNull() ?: 0
        }

        holder.etRok.addTextChangedListener {
            listData[position].rok =
                it.toString().toIntOrNull() ?: 0
        }

        holder.etJilbab.addTextChangedListener {
            listData[position].jilbab =
                it.toString().toIntOrNull() ?: 0
        }

        holder.etKaos.addTextChangedListener {
            listData[position].kaos =
                it.toString().toIntOrNull() ?: 0
        }

        holder.etKeterangan.addTextChangedListener {
            listData[position].keterangan =
                it.toString()
        }
    }

    fun getAllData(): List<CustomerData> {
        return listData
    }
}