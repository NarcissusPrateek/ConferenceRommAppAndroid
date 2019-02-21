package com.example.conferencerommapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.conferencerommapp.Activity.BuildingDashboard
import com.example.conferencerommapp.Activity.DashBoardActivity
import com.example.conferencerommapp.services.ConferenceService
import com.example.globofly.services.Servicebuilder
import com.github.clans.fab.FloatingActionButton
import kotlinx.android.synthetic.main.activity_blocked_dashboard.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BlockedDashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blocked_dashboard)

        val addConferenceRoom: FloatingActionButton = findViewById(R.id.add_conference)
        val maintenance: FloatingActionButton = findViewById(R.id.maintenance)

        booking_detials.setOnClickListener {
            startActivity(Intent(this@BlockedDashboard, DashBoardActivity::class.java))

        }
        maintenance.setOnClickListener {
            val maintenanceintent= Intent(applicationContext,Spinner::class.java)
            startActivity(maintenanceintent)
            finish()
        }

        addConferenceRoom.setOnClickListener {
            val addConferenceintent = Intent(applicationContext,BuildingDashboard::class.java)
            startActivity(addConferenceintent)
            finish()
        }
    }
    override fun onResume() {
        super.onResume()
        loadBlocking()
    }

    private fun loadBlocking() {

        val blockServices = Servicebuilder.buildService(ConferenceService::class.java)
        val requestCall : Call<List<Blocked>> = blockServices.getBlockedConference()
        requestCall.enqueue(object: Callback<List<Blocked>> {
            override fun onFailure(call: Call<List<Blocked>>, t: Throwable) {

                Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Blocked>>, response: Response<List<Blocked>>) {
                Log.i("----------------", response.body().toString())
                if(response.isSuccessful) {
                    val blockedList: List<Blocked>? = response.body()
                    conference_blocked_list.adapter = BlockedRecyclerAdapter(blockedList!!)

                }
                else {
                    Toast.makeText(applicationContext,"Unable to Load ", Toast.LENGTH_LONG).show()
                }

            }

        })
    }
}
