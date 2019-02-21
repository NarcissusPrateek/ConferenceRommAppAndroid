package com.example.conferencerommapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.conferencerommapp.services.ConferenceService
import com.example.globofly.services.Servicebuilder
import kotlinx.android.synthetic.main.activity_spinner.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Spinner : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spinner)



        getBuilding()
    }

    private fun getBuilding() {
        val buildingapi = Servicebuilder.buildService(ConferenceService::class.java)
        val requestCall : Call<List<BuildingT>> = buildingapi.getBuildings()
        requestCall.enqueue(object : Callback<List<BuildingT>> {
            override fun onFailure(call: Call<List<BuildingT>>, t: Throwable) {

                Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<BuildingT>>, response: Response<List<BuildingT>>) {
                if(response.isSuccessful) {
                    response.body()?.let {
                        var items = mutableListOf<String>()
                        var items_id = mutableListOf<Int>()
                        for(item in it){
                            items.add(item.BName!!)
                            items_id.add(item.BId!!)
                        }
                        //val adapter = ArrayAdapter<String>(this@Spinner, android.R.layout., items)
                        buiding_Spinner.adapter = ArrayAdapter<String>(this@Spinner,android.R.layout.simple_list_item_1,items)
                        buiding_Spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                val conferenceapi  = Servicebuilder.buildService(ConferenceService::class.java)
                                val requestCall : Call<List<BuildingConference>> = conferenceapi.getBuildingsConference(items_id[position])
                                requestCall.enqueue(object : Callback<List<BuildingConference>>{
                                    override fun onFailure(call: Call<List<BuildingConference>>, t: Throwable) {
                                        Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
                                    }

                                    override fun onResponse(call: Call<List<BuildingConference>>,response: Response<List<BuildingConference>>) {
                                        if(response.isSuccessful){
                                            response.body()?.let {
                                                var conference_name= mutableListOf<String>()
                                                var conference_id = mutableListOf<Int>()

                                                for(item in it){
                                                    conference_name.add(item.CName!!)
                                                    conference_id.add(item.CId)
                                                }
                                               // val conferenceadapter = ArrayAdapter<String>(applicationContext,android.R.layout.,conference_name)
                                                conference_Spinner.adapter=ArrayAdapter<String>(this@Spinner,android.R.layout.simple_list_item_1,conference_name)
                                                conference_Spinner.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener{
                                                    override fun onNothingSelected(parent: AdapterView<*>?) {
                                                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                                    }

                                                    override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int,id: Long) {
                                                        val blockRoom : Button = findViewById(R.id.block_conference)
                                                        blockRoom.setOnClickListener {
                                                            var cid=conference_id[position]
                                                            blocking(cid)

                                                            val blockdashboard_intent = Intent(applicationContext, BlockedDashboard::class.java)
                                                            startActivity(blockdashboard_intent)
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }

                                })
                            }

                        }

                    }

                }
                else {
                    Toast.makeText(applicationContext,"Unable to Load ", Toast.LENGTH_LONG).show()
                }

            }

        })
    }

    private fun blocking(cid:Int) {
        val blockroomapi= Servicebuilder.buildService(ConferenceService::class.java)
        val requestCall : Call<ResponseBody> = blockroomapi.blockconference(cid)
        requestCall.enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(applicationContext,"Failed",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                startActivity(Intent(this@Spinner,BlockedDashboard::class.java))
                finish()
            }

        })
    }
}
