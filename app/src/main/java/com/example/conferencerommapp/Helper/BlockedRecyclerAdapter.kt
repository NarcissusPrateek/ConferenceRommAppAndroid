package com.example.conferencerommapp

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.conferencerommapp.services.ConferenceService
import com.example.globofly.services.Servicebuilder


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BlockedRecyclerAdapter(private val blockedList: List<Blocked>) : androidx.recyclerview.widget.RecyclerView.Adapter<BlockedRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.blocked_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.blocked = blockedList[position]
        holder.conferenceName.text = blockedList[position].CName
        holder.buildingName.text = blockedList[position].BName
        var id = blockedList[position].CId
        holder.unblock.setOnClickListener {
            var id = blockedList[position].CId
            unBlock(id)
        }
        holder.itemView.setOnClickListener { v ->
            var id = blockedList[position].CId
            Toast.makeText(v.context, id.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    private fun unBlock(id: Int?) {
        val unBlockApi = Servicebuilder.buildService(ConferenceService::class.java)
        val requestCall : Call<ResponseBody> = unBlockApi.unBlockingConferenceRoom(id!!)
        requestCall.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.i("Done","COOL")
            }

        })
    }

    override fun getItemCount(): Int {
        return blockedList.size
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val conferenceName : TextView = itemView.findViewById(R.id.conferenceRoomName)
        val buildingName : TextView = itemView.findViewById(R.id.buildingname)
        val unblock :Button = itemView.findViewById(R.id.unblock)
        var blocked: Blocked? = null



    }
}