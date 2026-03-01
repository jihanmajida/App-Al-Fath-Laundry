package com.example.alfathhlaundry.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    // Menggunakan lazy agar apiService diinisialisasi saat dibutuhkan
    private val apiService by lazy { RetrofitClient.getInstance(this) }
    private val repository by lazy { AuthRepository(apiService) }

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- LOGIKA AUTO-LOGIN ---
        val prefs = getSharedPreferences("SESSION", Context.MODE_PRIVATE)
        if (prefs.getBoolean("IS_LOGIN", false)) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return // Keluar dari onCreate agar tidak render layout login
        }

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
                    Log.d("LOGIN_UI", "Mencoba login dengan email: $email")
                    viewModel.login(email, password)
                }
            }
        }

        observeLogin()
    }

    private fun observeLogin() {
        viewModel.loginResult.observe(this) { result ->
            Log.d("LOGIN_UI", "Status Login: $result")

            when (result) {
                is Resource.Loading -> {
                    Log.d("LOGIN_UI", "Sedang memproses...")
                    // Di sini kamu bisa tampilkan ProgressBar.setEnabled(true)
                }

                is Resource.Success -> {
                    val token = result.data.access_token

                    if (token.isNullOrEmpty()) {
                        Log.e("LOGIN_UI", "Token null atau kosong dari server")
                        Toast.makeText(this, "Gagal mendapatkan akses token", Toast.LENGTH_SHORT).show()
                        return@observe
                    }

                    Log.d("LOGIN_UI", "Login Berhasil! Menyimpan token.")

                    // --- SIMPAN SESSION ---
                    getSharedPreferences("SESSION", Context.MODE_PRIVATE)
                        .edit()
                        .putString("TOKEN", token)
                        .putBoolean("IS_LOGIN", true)
                        .apply()

                    // --- PINDAH HALAMAN & CLEAR STACK ---
                    val intent = Intent(this, HomeActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
                    finish()
                }

                is Resource.Error -> {
                    Log.e("LOGIN_UI", "Error Login: ${result.message}")
                    Toast.makeText(this, "Login Gagal: ${result.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}