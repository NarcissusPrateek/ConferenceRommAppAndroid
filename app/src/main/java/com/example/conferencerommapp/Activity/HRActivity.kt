package com.example.conferencerommapp

import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.conferencerommapp.R

class HRActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr)


        Toast.makeText(applicationContext,"HR Activity",Toast.LENGTH_LONG).show()
    }
}