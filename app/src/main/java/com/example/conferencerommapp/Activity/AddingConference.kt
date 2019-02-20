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
        val buildingapi = Servicebuilder.buildService(ConferenceService::class.java)
        val requestCall: Call<List<BuildingT>> = buildingapi.getBuildings()
        requestCall.enqueue(object:Callback<List<BuildingT>>{
            override fun onFailure(call: Call<List<BuildingT>>, t: Throwable) {
                Toast.makeText(applicationContext,t.message,Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<BuildingT>>, response: Response<List<BuildingT>>) {
                if(response.isSuccessful) {
                    response.body()?.let {
                        var items = mutableListOf<String>()
                        var items_id = mutableListOf<Int>()
                        for (item in it) {
                            items.add(item.BName!!)
                            items_id.add(item.BId!!)
                        }
                        val adapter =ArrayAdapter<String>(this@AddingConference, android.R.layout.simple_list_item_1, items)
                        buiding_Spinner.adapter = ArrayAdapter<String>(
                            this@AddingConference,
                            android.R.layout.simple_spinner_dropdown_item,
                            items
                        )
                        buiding_Spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                                val addconferenceRoom: Button = findViewById(R.id.add_conference_room)



                                addconferenceRoom.setOnClickListener {
                                    val buildingId: Int = items_id[position]
                                    val conferenceRoom: EditText = findViewById(R.id.conference_Name)
                                    val conferenceCapcity: EditText = findViewById(R.id.conference_Capacity)
                                    var room = addConferenceRoom()
                                    room.BId = buildingId
                                    room.CName = conferenceRoom.text.toString()
                                    room.Capacity = conferenceCapcity.text.toString().toInt()

                                    addingRoom(room)

                                }
                            }


                        }
                    }
                }
        }
    })
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
                    Toast.makeText(applicationContext,"Successfull",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(applicationContext,"Unable to post",Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}