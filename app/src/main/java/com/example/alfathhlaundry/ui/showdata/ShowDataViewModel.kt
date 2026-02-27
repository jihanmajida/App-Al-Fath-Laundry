package com.example.alfathhlaundry.ui.showdata

import GrupWithCustomer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.alfathhlaundry.data.model.user.GrupData
import com.example.alfathhlaundry.data.repository.ShowDataRepository
import kotlinx.coroutines.launch

class ShowDataViewModel(
    private val repository: ShowDataRepository
) : ViewModel() {

    private val _showData = MutableLiveData<GrupWithCustomer>()
    val showData: LiveData<GrupWithCustomer> = _showData

    fun getShowData(id: Int) {
        viewModelScope.launch {
            val response = repository.getShowData(id)
            response?.let {
                _showData.postValue(it.data)
            }
        }
    }
}

class ViewModelFactory(
    private val repository: ShowDataRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShowDataViewModel(repository) as T
    }
}