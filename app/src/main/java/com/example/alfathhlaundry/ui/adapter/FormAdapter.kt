package com.example.alfathhlaundry.ui.adapter

import AddGrupRequest
import DetailLaundryRequest
import PelangganRequest
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.data.model.user.*

class FormAdapter(
    private val grupSementara: GrupSementara,
    private var pelangganList: ArrayList<Pelanggan>,
    private var detailList: ArrayList<DetailLaundry>,
    private val idGrupAsli: Int = 0
) : RecyclerView.Adapter<FormAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etNama: EditText = itemView.findViewById(R.id.etNama)
        val etBaju: EditText = itemView.findViewById(R.id.etBaju)
        val etRok: EditText = itemView.findViewById(R.id.etRok)
        val etJilbab: EditText = itemView.findViewById(R.id.etJilbab)
        val etKaos: EditText = itemView.findViewById(R.id.etKaos)
        val etKeterangan: EditText = itemView.findViewById(R.id.etKeterangan)

        var nameWatcher: TextWatcher? = null
        var bajuWatcher: TextWatcher? = null
        var rokWatcher: TextWatcher? = null
        var jilbabWatcher: TextWatcher? = null
        var kaosWatcher: TextWatcher? = null
        var ketWatcher: TextWatcher? = null
    }

    override fun getItemCount(): Int = pelangganList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_add_data_customer, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPos = holder.adapterPosition
        if (currentPos == RecyclerView.NO_POSITION) return

        removeWatchers(holder)

        // Ambil data dengan aman
        val pelanggan = pelangganList[currentPos]
        val detail = detailList[currentPos]

        // SET DATA KE UI
        holder.etNama.setText(pelanggan.nama_pelanggan)

        // Debugging: Jika field tetap kosong, artinya detail.baju bernilai 0 atau null
        holder.etBaju.setText(if (detail.baju == 0) "" else detail.baju.toString())
        holder.etRok.setText(if (detail.rok == 0) "" else detail.rok.toString())
        holder.etJilbab.setText(if (detail.jilbab == 0) "" else detail.jilbab.toString())
        holder.etKaos.setText(if (detail.kaos == 0) "" else detail.kaos.toString())
        holder.etKeterangan.setText(detail.keterangan ?: "")

        setupWatchers(holder, currentPos)
    }

    private fun removeWatchers(holder: ViewHolder) {
        holder.etNama.removeTextChangedListener(holder.nameWatcher)
        holder.etBaju.removeTextChangedListener(holder.bajuWatcher)
        holder.etRok.removeTextChangedListener(holder.rokWatcher)
        holder.etJilbab.removeTextChangedListener(holder.jilbabWatcher)
        holder.etKaos.removeTextChangedListener(holder.kaosWatcher)
        holder.etKeterangan.removeTextChangedListener(holder.ketWatcher)
    }

    private fun setupWatchers(holder: ViewHolder, position: Int) {
        holder.nameWatcher = createWatcher { pelangganList[position].nama_pelanggan = it }
        holder.bajuWatcher = createWatcher { detailList[position].baju = it.toIntOrNull() ?: 0 }
        holder.rokWatcher = createWatcher { detailList[position].rok = it.toIntOrNull() ?: 0 }
        holder.jilbabWatcher = createWatcher { detailList[position].jilbab = it.toIntOrNull() ?: 0 }
        holder.kaosWatcher = createWatcher { detailList[position].kaos = it.toIntOrNull() ?: 0 }
        holder.ketWatcher = createWatcher { detailList[position].keterangan = it }

        holder.etNama.addTextChangedListener(holder.nameWatcher)
        holder.etBaju.addTextChangedListener(holder.bajuWatcher)
        holder.etRok.addTextChangedListener(holder.rokWatcher)
        holder.etJilbab.addTextChangedListener(holder.jilbabWatcher)
        holder.etKaos.addTextChangedListener(holder.kaosWatcher)
        holder.etKeterangan.addTextChangedListener(holder.ketWatcher)
    }

    private fun createWatcher(onChanged: (String) -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) { onChanged(s?.toString() ?: "") }
        }
    }

    fun getUpdatedGrup(): AddGrupRequest {
        return AddGrupRequest(
            id = if (idGrupAsli != 0) idGrupAsli else null,
            tanggal = grupSementara.tanggal,
            jam = grupSementara.jam,
            kamar = grupSementara.kamar,
            berat = grupSementara.berat,
            jenisPakaian = grupSementara.jenisPakaian,
            jumlahOrang = pelangganList.size,
            statusData = "0",
            // Mapping dengan lebih teliti
            pelanggan = pelangganList.map {
                PelangganRequest(it.nama_pelanggan)
            },
            detail_laundry = detailList.map {
                DetailLaundryRequest(
                    baju = it.baju,
                    jilbab = it.jilbab,
                    rok = it.rok,
                    kaos = it.kaos,
                    keterangan = it.keterangan ?: ""
                )
            }
        )
    }
}