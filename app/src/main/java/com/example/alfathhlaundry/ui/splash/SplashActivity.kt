package com.example.alfathhlaundry.ui.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.alfathhlaundry.R
import com.example.alfathhlaundry.ui.home.HomeActivity
import com.example.alfathhlaundry.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Tunggu 2 detik (durasi splash screen)
        Handler(Looper.getMainLooper()).postDelayed({

            // 1. Ambil data session dari SharedPreferences
            val prefs = getSharedPreferences("SESSION", Context.MODE_PRIVATE)
            val isLoggedIn = prefs.getBoolean("IS_LOGIN", false)

            // 2. Tentukan arah halaman
            if (isLoggedIn) {
                // Jika sudah login, lempar ke Home
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                // Jika belum, lempar ke Login
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

            // 3. Tutup SplashActivity agar tidak bisa di-Back
            finish()

        }, 2000)
    }
}