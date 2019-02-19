package com.example.conferencerommapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.conferencerommapp.services.ConferenceService
import com.example.globofly.services.Servicebuilder

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
        val buildingapi = Servicebuilder.buildService(ConferenceService::class.java)
        val requestCall : Call<List<BuildingT>> = buildingapi.getBuildings()
        requestCall.enqueue(object : Callback<List<BuildingT>>{
            override fun onFailure(call: Call<List<BuildingT>>, t: Throwable) {
                Toast.makeText(this@ConferenceDashBoard,"Spinner Error",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<BuildingT>>, response: Response<List<BuildingT>>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        var items= mutableListOf<String>()
                        var items_id= mutableListOf<Int>()
                        for(item:BuildingT in it){
                            items.add(item.BName!!)
                            items_id.add(item.BId!!)
                        }
                        val adapter = ArrayAdapter<String>(this@ConferenceDashBoard, android.R.layout.simple_list_item_1, items)
                        building_Spinner.adapter = ArrayAdapter<String>(this@ConferenceDashBoard,android.R.layout.simple_spinner_dropdown_item,items)
                        building_Spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                        }


                    }
                }
            }

        })
    }


}

