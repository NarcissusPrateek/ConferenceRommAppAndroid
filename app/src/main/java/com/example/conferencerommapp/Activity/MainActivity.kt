package com.example.conferencerommapp.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.conferencerommapp.AddingConference
import com.example.conferencerommapp.BlockedDashboard
import com.example.conferencerommapp.R
import com.example.conferencerommapp.Spinner
import com.github.clans.fab.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addConferenceRoom: FloatingActionButton = findViewById(R.id.add_conference)
        val maintenance: FloatingActionButton = findViewById(R.id.maintenance)
        val blocked : Button = findViewById(R.id.blocked_room)



        blocked.setOnClickListener {
            val blockintent = Intent(applicationContext, BlockedDashboard::class.java)
            startActivity(blockintent)
        }

        maintenance.setOnClickListener {
            val maintenanceintent= Intent(applicationContext, Spinner::class.java)
            startActivity(maintenanceintent)
        }

        addConferenceRoom.setOnClickListener {
            val addConferenceintent = Intent(applicationContext, AddingConference::class.java)
            startActivity(addConferenceintent)
        }
    }
}
