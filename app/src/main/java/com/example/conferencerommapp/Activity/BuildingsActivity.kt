package com.example.conferencerommapp.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.conferencerommapp.Helper.BuildingAdapter
import com.example.conferencerommapp.Model.Building
import com.example.conferencerommapp.R
import com.example.conferencerommapp.services.ConferenceService
import com.example.globofly.services.Servicebuilder
import kotlinx.android.synthetic.main.activity_building_list.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


public class BuildingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_building_list)
        //val intent = getIntent().getStringExtra("FromTime")
        //val FromTime=intent.getStringExtra("FromTime")
     //   Toast.makeText(this@BuildingsActivity,intent + getIntent().getStringExtra("FromTime") + getIntent().getStringExtra("ToTime")+getIntent().getStringExtra("Date"),Toast.LENGTH_SHORT).show()
     }

    override fun onResume() {
        super.onResume()
        loadBuildings()
    }

    fun loadBuildings() {


        //Toast.makeText(this@BuildingsActivity,intent.getStringExtra("FromTime"),Toast.LENGTH_LONG).show()
         val bundle: Bundle? = intent.extras
        val from = bundle!!.get("FromTime").toString()
        val to = bundle.get("ToTime").toString()
        val date = bundle.get("Date").toString()
        val capacity = bundle.get("Capacity").toString()
        val DateFromTime = date + " " + from
        val DateToTime = date + " " + to
        Toast.makeText(applicationContext,DateFromTime,Toast.LENGTH_LONG).show()
        val conferenceService = Servicebuilder.buildService(ConferenceService::class.java)
        val requestCall : Call<List<Building>> = conferenceService.getBuildingList()
        requestCall.enqueue(object: Callback<List<Building>> {
            override fun onFailure(call: Call<List<Building>>, t: Throwable) {

                Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Building>>, response: Response<List<Building>>) {
                if(response.isSuccessful) {
                    val buildingList: List<Building>? = response.body()
                    Log.i("--------$$$$----",buildingList!!.isEmpty().toString())
                    if(buildingList!!.isEmpty()) {
                        val builder = AlertDialog.Builder(this@BuildingsActivity)

                        // Set the alert dialog title
                        builder.setTitle("Available Room Status")

                        // Display a message on alert dialog
                        builder.setMessage("Sorry! No Rooms Available")
                    }
                    else {
                        building_recycler_view.adapter = BuildingAdapter(buildingList!!,
                            object : BuildingAdapter.BtnClickListener{
                                override fun onBtnClick(buildingId: String?, buildingname: String?) {
                                    val intent = Intent(this@BuildingsActivity, ConferenceRoomActivity::class.java)
                                    intent.putExtra("BuildingId", buildingId)
                                    intent.putExtra("FromTime",DateFromTime)
                                    intent.putExtra("ToTime", DateToTime)
                                    intent.putExtra("Date",date)
                                    intent.putExtra("Capacity",capacity)
                                    intent.putExtra("BuildingName", buildingname)
                                    startActivity(intent)


                                    // Toast.makeText(this@BuildingsActivity, buildingId, Toast.LENGTH_LONG)
                                }

                            })
                    }

                }
                else {
                    Toast.makeText(applicationContext,"Unable to Load Buildings",Toast.LENGTH_LONG).show()
                }

            }

        })
    }
}