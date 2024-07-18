package com.adilashraf.orderkrouserapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    val currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser == null){
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this@SplashActivity, StartActivity::class.java))
                finish()
            }, 3000)
        }

    }

    override fun onStart() {
        super.onStart()
         if (currentUser != null) {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }
}