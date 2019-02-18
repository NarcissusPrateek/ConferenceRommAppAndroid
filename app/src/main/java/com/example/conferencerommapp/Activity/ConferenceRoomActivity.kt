package com.example.conferencerommapp.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.conferencerommapp.Helper.ConferenceRoomAdapter
import com.example.conferencerommapp.Model.ConferenceRoom
import com.example.conferencerommapp.Model.FetchConferenceRoom
import com.example.conferencerommapp.R
import com.example.conferencerommapp.services.ConferenceService
import com.example.globofly.services.Servicebuilder
import kotlinx.android.synthetic.main.activity_conference_room.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class ConferenceRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conference_room)

        //var building_id: String = intent.getStringExtra("BuildingId")
        //Toast.makeText(applicationContext, building_id, Toast.LENGTH_LONG).show()
        loadConferenceRoom()



    }

    override fun onResume() {
        super.onResume()
      //  loadConferenceRoom()
    }
    public fun loadConferenceRoom() {

        val bundle: Bundle = intent.extras
        val from = bundle.get("FromTime").toString()
        val to = bundle.get("ToTime").toString()
        val date = bundle.get("Date").toString()
        val capacity = bundle.get("Capacity").toString()
        val buildingId = bundle.get("BuildingId").toString()
        val building_name = bundle.get("BuildingName").toString()


        var inputs = FetchConferenceRoom()
        inputs.FromTime = from
        inputs.ToTime = to
        inputs.Capacity = capacity.toInt()
        inputs.BId = buildingId.toInt()



        val conferenceService = Servicebuilder.buildService(ConferenceService::class.java)
        val requestCall: Call<List<ConferenceRoom>> = conferenceService.getConferenceRoomList(inputs)
        requestCall.enqueue(object: Callback<List<ConferenceRoom>> {
            override fun onFailure(call: Call<List<ConferenceRoom>>, t: Throwable) {
                Toast.makeText(applicationContext, "on failure on loading rooms" + t.message, Toast.LENGTH_LONG).show()

            }

            override fun onResponse(call: Call<List<ConferenceRoom>>, response: Response<List<ConferenceRoom>>) {
                Log.i("-3-3-3-3-3----",response.body().toString())
                if(response.isSuccessful) {
                   var conferenceRoomList =  response.body()
                    conference_recycler_view.adapter = ConferenceRoomAdapter(conferenceRoomList!!,
                        object : ConferenceRoomAdapter.BtnClickListener {
                            override fun onBtnClick(roomId: String?,roomname: String?) {
                                //Toast.makeText(this@ConferenceRoomActivity,"Hello Pratheek",Toast.LENGTH_LONG).show()
                                val intent = Intent(this@ConferenceRoomActivity, BookingActivity::class.java)
                                intent.putExtra("RoomId", roomId)
                                intent.putExtra("BuildingId", buildingId)
                                intent.putExtra("FromTime",from)
                                intent.putExtra("ToTime", to)
                                intent.putExtra("Date",date)
                                intent.putExtra("Capacity",capacity)
                                intent.putExtra("RoomName",roomname)
                                intent.putExtra("BuildingName", building_name)
                                startActivity(intent)
                               // finish()

                            }
                        })
                }
                else {
                    Toast.makeText(applicationContext, "Unable to Load Conference Room", Toast.LENGTH_LONG).show()
                }

            }

        })
    }
}




/*

 */