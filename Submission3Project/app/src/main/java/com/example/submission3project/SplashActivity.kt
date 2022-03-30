package com.example.submission3project
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        var timeSplash: Long = 3000
        Handler().postDelayed({
            val transisi = Intent(this, MainActivity::class.java)
            startActivity(transisi)
            finish()
        }, timeSplash)
    }
}