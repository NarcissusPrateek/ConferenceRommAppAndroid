package com.example.conferencerommapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.conferencerommapp.R

class HRActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr)

        Toast.makeText(this@HRActivity,"HR ACTIVITY",Toast.LENGTH_LONG).show()
    }
}
