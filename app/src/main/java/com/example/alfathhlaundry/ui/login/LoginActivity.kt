package com.example.alfathhlaundry.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log // 🔥 TAMBAHAN
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.data.network.RetrofitClient
import com.example.alfathhlaundry.data.repository.AuthRepository
import com.example.alfathhlaundry.ui.home.HomeActivity
import com.example.alfathhlaundry.utils.Resource

class LoginActivity : AppCompatActivity() {

    // 🔧 SUDAH BENAR
    val apiService = RetrofitClient.getInstance(this)
    val repository = AuthRepository(apiService)

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {

            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            when {
                email.isEmpty() || password.isEmpty() -> {
                    Toast.makeText(this, "Email dan Password wajib diisi", Toast.LENGTH_SHORT).show()
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    Toast.makeText(this, "Format email tidak valid", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Log.d("LOGIN_UI", "Button clicked") // 🔥 TAMBAHAN
                    viewModel.login(email, password)
                }
            }
        }

        observeLogin()
    }

    private fun observeLogin() {
        viewModel.loginResult.observe(this) { result ->

            Log.d("LOGIN_UI", "State: $result") // 🔥 TAMBAHAN (WAJIB)

            when (result) {
                is Resource.Loading -> {
                    Log.d("LOGIN_UI", "Loading...") // 🔥 TAMBAHAN
                    // ProgressBar optional
                }

                is Resource.Success -> {

                    val token = result.data.access_token

                    // 🔥 TAMBAHAN: validasi token
                    if (token.isEmpty()) {
                        Toast.makeText(this, "Token kosong", Toast.LENGTH_SHORT).show()
                        return@observe
                    }

                    Log.d("LOGIN_UI", "Login sukses, token: $token") // 🔥 TAMBAHAN

                    // Simpan session
                    getSharedPreferences("SESSION", MODE_PRIVATE)
                        .edit()
                        .putString("TOKEN", token)
                        .putBoolean("IS_LOGIN", true)
                        .apply()

                    // 🔥 PINDAH HALAMAN (INI KUNCI)
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                    finish() // 🔥 TAMBAHAN (BIAR TIDAK BISA BACK KE LOGIN)
                }

                is Resource.Error -> {
                    Log.e("LOGIN_UI", "Error: ${result.message}") // 🔥 TAMBAHAN
                    Toast.makeText(
                        this,
                        result.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}