package com.example.conferencerommapp

import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.conferencerommapp.R

class InputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        Toast.makeText(applicationContext,"Input Control",Toast.LENGTH_LONG).show()
    }
}