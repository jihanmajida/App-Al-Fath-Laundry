package com.example.alfathhlaundry.ui.grup

import AddGrupRequest
import GrupWithCustomer
import android.util.Log
import androidx.lifecycle.*
import com.example.alfathhlaundry.data.model.user.*
import com.example.alfathhlaundry.data.repository.GrupRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GrupViewModel(private val repository: GrupRepository) : ViewModel() {

    val grups: MutableLiveData<List<GrupWithCustomer>> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val successMessage: MutableLiveData<String> = MutableLiveData()

    fun loadGrup(tanggal: String) {
        viewModelScope.launch {
            try {
                val data = repository.getTodayData(tanggal)
                grups.value = data
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Gagal memuat data hari ini"
            }
        }
    }

    fun addGrup(request: AddGrupRequest) {
        viewModelScope.launch {
            try {
                Log.d("API_TEST","Hit API ADD")

                repository.addGrup(request) // ✅ sudah throw exception jika gagal

                successMessage.value = "Grup berhasil ditambahkan"

                // 🔥 FIX REFRESH TANGGAL
                val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    .format(Date())

                loadGrup(today)

            } catch (e: Exception) {
                Log.e("API_TEST","Error ADD: ${e.message}")
                errorMessage.value = e.message
            }
        }
    }

    fun updateGrup(id: Int, request: AddGrupRequest) {
        viewModelScope.launch {
            try {
                Log.d("API_TEST","Hit API Update")

                repository.updateGrup(id, request) // ✅ Unit, throw exception jika gagal

                Log.d("API_TEST","Success Update")
                successMessage.value = "Update berhasil"

                // 🔥 Reload grup
                val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                loadGrup(today)

            } catch (e: Exception) {
                Log.e("API_TEST","Error update: ${e.message}")
                errorMessage.value = e.message
            }
        }
    }
}

class GrupViewModelFactory(private val repository: GrupRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GrupViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GrupViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}