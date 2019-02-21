package com.example.conferencerommapp

import android.content.Intent
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

class AddingConference : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_conference)

        AddRoom()
    }

    private fun AddRoom() {
        val bundle: Bundle = intent.extras
        val buildingId = bundle.get("BuildingId").toString().toInt()
        val addconferenceRoom:Button = findViewById(R.id.add_conference_room)
        addconferenceRoom.setOnClickListener {
            val conferenceRoom: EditText = findViewById(R.id.conference_Name)
            val conferenceCapcity: EditText = findViewById(R.id.conference_Capacity)
            var room = addConferenceRoom()
            room.BId = buildingId
            room.CName = conferenceRoom.text.toString()
            room.Capacity = conferenceCapcity.text.toString().toInt()
            if(room.CName != null && room.Capacity != 0) {
                addingRoom(room)
                val intent=Intent(this,ConferenceDashBoard::class.java)
                intent.putExtra("BuildingId", buildingId)
                startActivity(intent)
            }
            else {
                Toast.makeText(this@AddingConference,"Please fill all boxes.....",Toast.LENGTH_LONG).show()
            }

        }
}
    private fun addingRoom(room: addConferenceRoom) {
        val conferenceRoomapi =Servicebuilder.buildService(ConferenceService::class.java)
        val addconferencerequestCall: Call<ResponseBody> = conferenceRoomapi.addConference(room)
        addconferencerequestCall.enqueue(object:Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(applicationContext,"onFailure",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    startActivity(Intent(this@AddingConference,ConferenceDashBoard::class.java))

                    Toast.makeText(applicationContext,"Successfull",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(applicationContext,"Unable to post",Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}