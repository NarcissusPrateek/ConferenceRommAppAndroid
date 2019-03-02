package com.example.conferencerommapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.example.conferencerommapp.Activity.BuildingDashboard
import com.example.conferencerommapp.Activity.DashBoardActivity
import com.example.conferencerommapp.services.ConferenceService
import com.example.globofly.services.Servicebuilder
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_blocked_dashboard.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BlockedDashboard : AppCompatActivity() {

    var mGoogleSignInClient: GoogleSignInClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blocked_dashboard)
        val actionbar=supportActionBar
        actionbar!!.setTitle("Blocked Rooms")



        val addConferenceRoom: FloatingActionButton = findViewById(R.id.add_conference)
        val maintenance: FloatingActionButton = findViewById(R.id.maintenance)
        val booking_details:FloatingActionButton=findViewById(R.id.booking_detials)
        val menu:FloatingActionMenu=findViewById(R.id.menu)
        menu.setClosedOnTouchOutside(true);
        booking_detials.setOnClickListener {
            startActivity(Intent(this@BlockedDashboard, DashBoardActivity::class.java))
            finish()
        }
        maintenance.setOnClickListener {
            val maintenanceintent= Intent(applicationContext,Spinner::class.java)
            startActivity(maintenanceintent)
            //finish()
        }

        addConferenceRoom.setOnClickListener {
            val addConferenceintent = Intent(applicationContext,BuildingDashboard::class.java)
            startActivity(addConferenceintent)
            //finish()
        }





    }
    override fun onBackPressed() {
        super.onBackPressed()
        //finishAffinity();
        //finish()

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
                    if(blockedList!!.size == 0){
                        Toast.makeText(applicationContext,"You haven't block any Room", Toast.LENGTH_LONG).show()

                    }
                    conference_blocked_list.adapter = BlockedRecyclerAdapter(blockedList!!,this@BlockedDashboard)

                }
                else {
                    Toast.makeText(applicationContext,"Unable to Load ", Toast.LENGTH_LONG).show()
                }

            }

        })
    }
}
