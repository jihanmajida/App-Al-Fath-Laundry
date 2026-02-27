package com.example.alfathhlaundry.ui.home

import GrupWithCustomer
import android.util.Log
import androidx.lifecycle.*
import com.example.alfathhlaundry.data.repository.GrupRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: GrupRepository
) : ViewModel() {

    private val _grupData = MutableLiveData<List<GrupWithCustomer>>()
    val grupData: LiveData<List<GrupWithCustomer>> = _grupData

    private var selectedDate: String? = null

    // Load data berdasarkan tanggal
    // Di dalam HomeViewModel.kt
    fun loadDataByDate(tanggal: String) {
        selectedDate = tanggal
        viewModelScope.launch {
            try {
                val result = repo.getTodayData(tanggal)
                // CEK DI LOGCAT: Apakah hasil 'result' ini berubah-ubah sesuai tanggal?
                android.util.Log.d("API_RESULT", "Tanggal: $tanggal, Jumlah Data: ${result.size}")
                _grupData.postValue(result)
            } catch (e: Exception) {
                _grupData.postValue(emptyList())
            }
        }
    }

    // Reload data sesuai tanggal aktif
    fun reload() {
        selectedDate?.let { loadDataByDate(it) }
    }

    // Delete grup
    fun deleteGrup(id: Int) {
        viewModelScope.launch {
            try {
                repo.deleteGrup(id)
                reload() // reload LiveData supaya UI update
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Delete error", e)
            }
        }
    }

    // Update status
    fun updateStatus(id: Int, status: String) {
        viewModelScope.launch {
            try {
                repo.updateStatus(id, status)
                reload() // reload LiveData supaya checkbox update
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Update status error", e)
            }
        }
    }
}

class HomeViewModelFactory(
    private val repo: GrupRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repo) as T
    }
}