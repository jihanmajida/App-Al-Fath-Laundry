package com.example.alfathhlaundry.ui.login

import androidx.lifecycle.*
import com.example.alfathhlaundry.data.model.user.LoginResponse
import com.example.alfathhlaundry.data.repository.AuthRepository
import com.example.alfathhlaundry.utils.Resource
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginResult = MutableLiveData<Resource<LoginResponse>>()
    val loginResult: LiveData<Resource<LoginResponse>> = _loginResult

    fun login(email: String, password: String) {
        _loginResult.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val response = repository.login(email, password) // Response<LoginResponse>

                if (response.isSuccessful) {
                    val result = response.body() ?: throw Exception("Response body null")

                    // kalau dapat token → LOGIN SUKSES
                    if (!result.access_token.isNullOrEmpty()) {
                        _loginResult.value = Resource.Success(result)
                    } else {
                        _loginResult.value = Resource.Error("Login gagal: token kosong")
                    }

                } else {
                    _loginResult.value = Resource.Error("Login gagal: ${response.code()} ${response.message()}")
                }

            } catch (e: Exception) {
                _loginResult.value = Resource.Error(e.message ?: "Login gagal")
            }
        }
    }
}

class LoginViewModelFactory(
    private val repository: AuthRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}