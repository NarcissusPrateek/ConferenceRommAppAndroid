package com.example.conferencerommapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.conferencerommapp.services.ConferenceService
import com.example.globofly.services.Servicebuilder
import com.example.myapplication.Models.ConferenceList

import kotlinx.android.synthetic.main.activity_blocked_dashboard.*
import kotlinx.android.synthetic.main.activity_conference_dash_board.*
import kotlinx.android.synthetic.main.activity_spinner.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConferenceDashBoard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conference_dash_board)

        getConference()
    }

    private fun getConference() {
        val buildingapi: ConferenceService = Servicebuilder.buildService(ConferenceService::class.java)
        val requestCall : Call<List<BuildingT>> = buildingapi.getBuildings()


        requestCall.enqueue(object :Callback<List<BuildingT>>{
            override fun onFailure(call: Call<List<BuildingT>>, t: Throwable) {
                Toast.makeText(this@ConferenceDashBoard,"Unable to connecct to load Building",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<BuildingT>>, response: Response<List<BuildingT>>) {
                if(response.isSuccessful){
                    response.body().let {
                        var buildingname= mutableListOf<String>()
                        val buildingId= mutableListOf<Int>()

                        for(building in it!!){
                            buildingname.add(building.BName!!)
                            buildingId.add(building.BId!!)
                        }

                        spinner.adapter=ArrayAdapter<String>(this@ConferenceDashBoard,android.R.layout.simple_spinner_dropdown_item,buildingname)
                        spinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }

                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                val requestCall:Call<List<ConferenceList>> = buildingapi.conferencelist(buildingId[position])
                                requestCall.enqueue(object :Callback<List<ConferenceList>>{
                                    override fun onFailure(call: Call<List<ConferenceList>>, t: Throwable) {
                                        Toast.makeText(this@ConferenceDashBoard,"Failure of Recycler View",Toast.LENGTH_LONG).show()
                                    }

                                    override fun onResponse(call: Call<List<ConferenceList>>, response: Response<List<ConferenceList>>) {
                                        Log.i("@@@@@@@@@@",response.body().toString())
                                        if(response.isSuccessful){
                                            Log.i("@@@@@@@@@@",response.body().toString())
                                            val conferencelist:List<ConferenceList>? = response.body()
                                            conference_list.adapter=ConferenceRecyclerAdapter(conferencelist!!)
                                        }
                                        else{
                                            Toast.makeText(this@ConferenceDashBoard,"unable to load Recycler View",Toast.LENGTH_LONG).show()
                                        }
                                    }

                                })
                            }

                        }

                    }
                }
            }

        })
    }



}

