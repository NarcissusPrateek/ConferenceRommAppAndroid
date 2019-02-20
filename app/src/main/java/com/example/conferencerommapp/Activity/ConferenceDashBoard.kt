package com.example.conferencerommapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.conferencerommapp.services.ConferenceService
import com.example.globofly.services.Servicebuilder
import com.example.myapplication.Models.ConferenceList
import com.github.clans.fab.FloatingActionButton

import kotlinx.android.synthetic.main.activity_conference_dash_board.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConferenceDashBoard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conference_dash_board)
        val bundle: Bundle = intent.extras
        val buildingId = bundle.get("BuildingId").toString().toInt()
        var addConference: FloatingActionButton =findViewById(R.id.add_conferenece)
        addConference.setOnClickListener {
            startActivity(Intent(this,AddingConference::class.java))
        }
        getConference(buildingId)
    }

    private fun getConference(buildingId: Int) {
        val conferenceService = Servicebuilder.buildService(ConferenceService::class.java)
        val requestCall : Call<List<ConferenceList>> = conferenceService.conferencelist(buildingId)
        requestCall.enqueue(object :Callback<List<ConferenceList>>{
            override fun onFailure(call: Call<List<ConferenceList>>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

