package com.example.conferencerommapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.conferencerommapp.BuildingT
import com.example.conferencerommapp.ConferenceDashBoard
import com.example.conferencerommapp.Helper.BuildingRecyclerAdapter
import com.example.conferencerommapp.R
import com.example.conferencerommapp.services.ConferenceService
import com.example.globofly.services.Servicebuilder
import com.github.clans.fab.FloatingActionButton
import kotlinx.android.synthetic.main.activity_building_dashboard.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuildingDashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building_dashboard)
        val addBuilding : FloatingActionButton = findViewById(R.id.add_building)
        addBuilding.setOnClickListener {
            startActivity(Intent(this,AddingBuilding::class.java))
        }
    }
    override fun onResume() {
        super.onResume()
        loadBuildings()
    }

    fun loadBuildings() {


        //Toast.makeText(this@BuildingsActivity,intent.getStringExtra("FromTime"),Toast.LENGTH_LONG).show()
        val conferenceService = Servicebuilder.buildService(ConferenceService::class.java)
        val requestCall : Call<List<BuildingT>> = conferenceService.getBuildings()
        requestCall.enqueue(object: Callback<List<BuildingT>> {
            override fun onFailure(call: Call<List<BuildingT>>, t: Throwable) {

                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<BuildingT>>, response: Response<List<BuildingT>>) {
                if (response.isSuccessful)
                {
                    val buildingList: List<BuildingT>? = response.body()
                    Log.i("--------$$$$----", buildingList!!.isEmpty().toString())
                    buidingRecyclerView.adapter = BuildingRecyclerAdapter(buildingList!!,
                        object : BuildingRecyclerAdapter.BtnClickListener
                        {
                            override fun onBtnClick(buildingId: Int?, buildingname: String?)
                            {
                                var intent = Intent(this@BuildingDashboard, ConferenceDashBoard::class.java)
                                intent.putExtra("BuildingId", buildingId)
                                startActivity(intent)

                                Toast.makeText(this@BuildingDashboard,buildingId.toString(), Toast.LENGTH_SHORT).show()

                                // Toast.makeText(this@BuildingsActivity, buildingId, Toast.LENGTH_LONG)
                            }

                        })
                }
                else {
                    Toast.makeText(applicationContext, "Unable to Load Buildings", Toast.LENGTH_LONG).show()
                }

            }

        })
    }
}