package com.example.conferencerommapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.conferencerommapp.Helper.Conference_Room_adapter_new
import com.example.conferencerommapp.services.ConferenceService
import com.example.globofly.services.Servicebuilder
import com.example.myapplication.Models.ConferenceList
import com.github.clans.fab.FloatingActionButton

import kotlinx.android.synthetic.main.activity_conference_dash_board.*
import kotlinx.android.synthetic.main.dashboard_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConferenceDashBoard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conference_dash_board)
        val bundle: Bundle = intent.extras
        val buildingId = bundle.get("BuildingId").toString().toInt()
        var addConference: FloatingActionButton=findViewById(R.id.add_conferenece)
        addConference.setOnClickListener {

            //shared preference code

            var pref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
            val  editor = pref.edit()
            editor.putInt("BuildingId",buildingId)
            editor.apply()

            val intent=Intent(this,AddingConference::class.java)
            intent.putExtra("BuildingId", buildingId)
            startActivity(intent)
            //finish()
        }
        getConference(buildingId)
    }

  /*  override fun onStart() {
        super.onStart()

    }*/
    override fun onRestart() {
        super.onRestart()
        val pref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        var buildingId = pref.getInt("BuildingId",0)
        //Toast.makeText(this@ConferenceDashBoard,buildingId,Toast.LENGTH_LONG).show()
        getConference(buildingId)
    }
    private fun getConference(buildingId: Int) {
        val conferenceService = Servicebuilder.buildService(ConferenceService::class.java)
        val requestCall : Call<List<ConferenceList>> = conferenceService.conferencelist(buildingId)
        requestCall.enqueue(object :Callback<List<ConferenceList>>{
            override fun onFailure(call: Call<List<ConferenceList>>, t: Throwable) {
                Toast.makeText(this@ConferenceDashBoard,"Failure",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<ConferenceList>>, response: Response<List<ConferenceList>>) {
                if(response.isSuccessful){

                    val conferencelist:List<ConferenceList>? = response.body()
                    conference_list.adapter= ConferenceRecyclerAdapter(conferencelist!!)
                }
                else{
                    Toast.makeText(this@ConferenceDashBoard,"unable to load Recycler View",Toast.LENGTH_LONG).show()
                }
            }

        })
    }
}

