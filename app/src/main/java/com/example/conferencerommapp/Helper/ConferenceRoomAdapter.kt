package com.example.conferencerommapp.Helper

import android.content.Intent
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.conferencerommapp.Activity.BookingActivity
import com.example.conferencerommapp.Activity.ConferenceRoomActivity
import com.example.conferencerommapp.Model.Building
import com.example.conferencerommapp.Model.ConferenceRoom
import com.example.conferencerommapp.R
import java.sql.Time
import java.util.*


class ConferenceRoomAdapter(private val conferenceRoomList: List<ConferenceRoom>, val btnlistener: BtnClickListener) : androidx.recyclerview.widget.RecyclerView.Adapter<ConferenceRoomAdapter.ViewHolder>() {

	companion object {
		var mClickListener: BtnClickListener? = null
	}
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_conference_room, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		mClickListener = btnlistener
		holder.conferenceRoom = conferenceRoomList[position]

		holder.txvRoom.text = conferenceRoomList[position].conf_name

		holder.txvRoomCapacity.text = conferenceRoomList[position].conf_capacity


//        if(conferenceRoomList[position].Status.equals("Available"))
//        {
//            holder.button!!.setBackgroundColor(Color.GREEN)
//            holder.button!!.setOnClickListener {
//                Log.i("--------","Room is Available")
//            }
//        }else if(conferenceRoomList[position].Status.equals("Booked")){
//            holder.button!!.setBackgroundColor(Color.RED)
//            holder.button!!.setEnabled(false)
//        }
//        else {
//            holder.button!!.setBackgroundColor(Color.YELLOW)
//            holder.button!!.setEnabled(false)
//        }


        holder.itemView.setOnClickListener { v ->
			val context = v.context
			val roomId = conferenceRoomList[position].conf_id
			val roomname = conferenceRoomList[position].conf_name
			mClickListener?.onBtnClick(roomId.toString(),roomname)
		}
	}

	override fun getItemCount(): Int {
		return conferenceRoomList.size
	}

	class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

		val txvRoom: TextView = itemView.findViewById(R.id.txv_room)
		val txvRoomCapacity: TextView = itemView.findViewById(R.id.txv_room_capacity)
     //   var button: Button? = null
        var conferenceRoom: ConferenceRoom? = null

		override fun toString(): String {
			return """${super.toString()} '${txvRoom.text}'"""
		}
	}
	open interface BtnClickListener {
		fun onBtnClick(roomId: String?,roomname: String?)
	}

}