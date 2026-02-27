package com.example.alfathhlaundry.ui.search

import GrupWithCustomer
import androidx.lifecycle.*
import com.example.alfathhlaundry.data.repository.GrupRepository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: GrupRepository
) : ViewModel() {

    private val _searchResult = MutableLiveData<List<GrupWithCustomer>>()
    val searchResult: LiveData<List<GrupWithCustomer>> = _searchResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun search(keyword: String) {
        viewModelScope.launch {
            try {
                val result = repository.searchGrup(keyword)

                // urut terbaru → terlama
                _searchResult.value = result.reversed()

            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}

class SearchViewModelFactory(
    private val repository: GrupRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(repository) as T
    }
}