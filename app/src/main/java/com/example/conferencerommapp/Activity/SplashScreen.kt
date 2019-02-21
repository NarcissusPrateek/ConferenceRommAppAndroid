package com.example.conferencerommapp.Activity

import android.content.Intent
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.conferencerommapp.BlockedDashboard
import com.example.conferencerommapp.R
import com.example.conferencerommapp.RegistrationActivity
import com.example.conferencerommapp.SignIn
import com.google.android.gms.auth.api.signin.GoogleSignIn


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val logoHandler  : Handler = Handler()
        val logoRunnable : Runnable = Runnable {
                val intent : Intent = Intent(applicationContext,BlockedDashboard::class.java)
                startActivity(intent)
                finish()
        }
        logoHandler.postDelayed(logoRunnable,3000)
    }
}

