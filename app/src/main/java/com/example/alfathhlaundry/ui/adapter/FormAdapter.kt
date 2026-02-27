package com.example.alfathhlaundry.ui.adapter

import AddGrupRequest
import DetailLaundryRequest
import GrupWithCustomer
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etNama: EditText = itemView.findViewById(R.id.etNama)
        val etBaju: EditText = itemView.findViewById(R.id.etBaju)
        val etRok: EditText = itemView.findViewById(R.id.etRok)
        val etJilbab: EditText = itemView.findViewById(R.id.etJilbab)
        val etKaos: EditText = itemView.findViewById(R.id.etKaos)
        val etKeterangan: EditText = itemView.findViewById(R.id.etKeterangan)

        // Variabel untuk menyimpan listener agar bisa dihapus (mencegah duplikasi)
        var nameWatcher: TextWatcher? = null
        var bajuWatcher: TextWatcher? = null
        var rokWatcher: TextWatcher? = null
        var jilbabWatcher: TextWatcher? = null
        var kaosWatcher: TextWatcher? = null
        var ketWatcher: TextWatcher? = null
    }

    override fun getItemCount(): Int = pelangganList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_add_data_customer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pelanggan = pelangganList.getOrNull(position) ?: Pelanggan(
            id_pelanggan = 0, // atau "" jika tipe datanya String
            nama_pelanggan = ""
        )

        val detail = detailList.getOrNull(position) ?: DetailLaundry(
            id = null,
            id_grup = idGrupAsli,
            id_pelanggan = 0,
            baju = 0,
            rok = 0,
            jilbab = 0,
            kaos = 0,
            keterangan = ""
        )

        removeWatchers(holder)

        // Set Data ke View
        holder.etNama.setText(pelanggan.nama_pelanggan)
        holder.etBaju.setText(detail.baju.toString())
        holder.etRok.setText(detail.rok.toString())
        holder.etJilbab.setText(detail.jilbab.toString())
        holder.etKaos.setText(detail.kaos.toString())
        holder.etKeterangan.setText(detail.keterangan ?: "")

        setupWatchers(holder, position)
    }

    private fun setupWatchers(holder: ViewHolder, position: Int) {
        // Kita gunakan holder.adapterPosition karena bindingAdapterPosition merah (masalah versi library)
        // adapterPosition akan mengambil index terbaru secara real-time saat user mengetik

        holder.nameWatcher = holder.etNama.addSimpleWatcher { text ->
            val currentPos = holder.adapterPosition
            if (currentPos != RecyclerView.NO_POSITION && currentPos < pelangganList.size) {
                pelangganList[currentPos].nama_pelanggan = text
            }
        }

        holder.bajuWatcher = holder.etBaju.addSimpleWatcher { text ->
            val currentPos = holder.adapterPosition
            if (currentPos != RecyclerView.NO_POSITION && currentPos < detailList.size) {
                detailList[currentPos].baju = text.toIntOrNull() ?: 0
            }
        }

        holder.rokWatcher = holder.etRok.addSimpleWatcher { text ->
            val currentPos = holder.adapterPosition
            if (currentPos != RecyclerView.NO_POSITION && currentPos < detailList.size) {
                detailList[currentPos].rok = text.toIntOrNull() ?: 0
            }
        }

        holder.jilbabWatcher = holder.etJilbab.addSimpleWatcher { text ->
            val currentPos = holder.adapterPosition
            if (currentPos != RecyclerView.NO_POSITION && currentPos < detailList.size) {
                detailList[currentPos].jilbab = text.toIntOrNull() ?: 0
            }
        }

        holder.kaosWatcher = holder.etKaos.addSimpleWatcher { text ->
            val currentPos = holder.adapterPosition
            if (currentPos != RecyclerView.NO_POSITION && currentPos < detailList.size) {
                detailList[currentPos].kaos = text.toIntOrNull() ?: 0
            }
        }

        holder.ketWatcher = holder.etKeterangan.addSimpleWatcher { text ->
            val currentPos = holder.adapterPosition
            if (currentPos != RecyclerView.NO_POSITION && currentPos < detailList.size) {
                detailList[currentPos].keterangan = text
            }
        }
    }

    private fun removeWatchers(holder: ViewHolder) {
        holder.etNama.removeTextChangedListener(holder.nameWatcher)
        holder.etBaju.removeTextChangedListener(holder.bajuWatcher)
        holder.etRok.removeTextChangedListener(holder.rokWatcher)
        holder.etJilbab.removeTextChangedListener(holder.jilbabWatcher)
        holder.etKaos.removeTextChangedListener(holder.kaosWatcher)
        holder.etKeterangan.removeTextChangedListener(holder.ketWatcher)
    }

    // Extension function untuk meringkas TextWatcher
    private fun EditText.addSimpleWatcher(onChanged: (String) -> Unit): TextWatcher {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { onChanged(s.toString()) }
            override fun afterTextChanged(s: Editable?) {}
        }
        this.addTextChangedListener(watcher)
        return watcher
    }

    fun getUpdatedGrup(): AddGrupRequest {
        val mappedPelanggan = pelangganList.map {
            PelangganRequest(nama_pelanggan = it.nama_pelanggan)
        }

        val mappedDetail = detailList.map {
            DetailLaundryRequest(
                baju = it.baju,
                jilbab = it.jilbab,
                rok = it.rok,
                kaos = it.kaos,
                keterangan = it.keterangan
            )
        }

        return AddGrupRequest(
            id = idGrupAsli,
            tanggal = grupSementara.tanggal,
            jam = grupSementara.jam,
            kamar = grupSementara.kamar,
            berat = grupSementara.berat,
            jenisPakaian = grupSementara.jenisPakaian, // Pastikan ini terisi string (bukan null)
            jumlahOrang = grupSementara.jumlahOrang,
            statusData = "0",
            pelanggan = mappedPelanggan,
            detail_laundry = mappedDetail
        )
    }
}