package com.example.conferencerommapp

import android.app.AlertDialog
import android.app.ProgressDialog
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

    //var progressDialog: ProgressDialog? = null
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
            if(conferenceRoom.text.isEmpty()) {
                Toast.makeText(this@AddingConference,"Please Enter Room Name",Toast.LENGTH_LONG).show()
            }
            else if(conferenceCapcity.text.isEmpty()) {
                Toast.makeText(this@AddingConference,"Please Enter Room Capacity",Toast.LENGTH_LONG).show()
            }
            else {
                room.BId = buildingId
                room.CName = conferenceRoom.text.toString()
                room.Capacity = conferenceCapcity.text.toString().toInt()
                addingRoom(room)
            }
            //var intent = Intent(this@AddingConference,ConferenceDashBoard::class.java)
            //intent.putExtra("BuildingId",buildingId)
            //startActivity(intent)
            //finish()

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
                    val builder = AlertDialog.Builder(this@AddingConference)
                    builder.setTitle("Status")
                    builder.setMessage("Room added Successfully.")
                    builder.setPositiveButton("Ok"){dialog, which ->
                        finish()
                    }
                    val dialog: AlertDialog = builder.create()
                    dialog.setCanceledOnTouchOutside(false)
                    dialog.show()
                }
                else{
                    Toast.makeText(applicationContext,"Unable to post",Toast.LENGTH_SHORT).show()
                }
            }

        })

    }
}