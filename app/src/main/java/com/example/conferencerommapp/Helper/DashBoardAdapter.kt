package com.example.conferencerommapp.Helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.conferencerommapp.Activity.DashBoardActivity
import com.example.conferencerommapp.Model.CancelBooking
import com.example.conferencerommapp.Model.Dashboard
import com.example.conferencerommapp.R
import com.example.conferencerommapp.services.ConferenceService
import com.example.globofly.services.Servicebuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DashBoardAdapter(val dashboardItemList: List<Dashboard>,val contex: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<DashBoardAdapter.ViewHolder>() {


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val view = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_list, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {

		holder.dashboard = dashboardItemList[position]
        holder.txvBName.text = dashboardItemList[position].BName
        holder.txvRoomName.text = dashboardItemList[position].CName
		holder.txvToTime.text = dashboardItemList[position].ToTime
		//holder.txvFrom.text = dashboardItemList[position].FromTime
		holder.txvPurpose.text = dashboardItemList[position].Purpose

		var fromtime = dashboardItemList[position].FromTime
		var totime = dashboardItemList[position].ToTime
		var datefrom = fromtime!!.split("T")
		var dateto = totime!!.split("T")
		holder.txvDate.text = datefrom.get(0)
		holder.txvFrom.text = datefrom.get(1)
		holder.txvToTime.text = dateto.get(1)
		holder.btnCancel.setOnClickListener{
			var cancel = CancelBooking()
			cancel.CId = dashboardItemList.get(position).CId
			cancel.ToTime = totime
			cancel.FromTime = fromtime
			cancel.Email = dashboardItemList.get(position).Email
			cancelBooking(cancel,contex)

		}

	}
	 private fun cancelBooking(cancel: CancelBooking,contex: Context) {
		val service = Servicebuilder.buildService(ConferenceService::class.java )
		val requestCall : Call<Int> = service.cancelBooking(cancel)
		requestCall.enqueue(object: Callback<Int> {
			override fun onFailure(call: Call<Int>, t: Throwable) {
				Toast.makeText(contex,"Error on Failure", Toast.LENGTH_LONG).show()
			}
			override fun onResponse(call: Call<Int>, response: Response<Int>) {
				Log.i("-------@@@-----",response.body().toString())
				if(response.isSuccessful) {
					val code = response.body()
					Toast.makeText(contex,"Booking Canceled with code ${code}", Toast.LENGTH_LONG).show()
						startActivity(contex,Intent(contex, DashBoardActivity::class.java),null)
					(contex as Activity).finish()


				}
				else {
					Toast.makeText(contex,"Response Error", Toast.LENGTH_LONG).show()
				}

			}

		})
	}
	override fun getItemCount(): Int {
		return dashboardItemList.size
	}

	class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

		val txvBName: TextView = itemView.findViewById(R.id.building_name)
		val txvRoomName: TextView = itemView.findViewById(R.id.conferenceRoomName)
        val txvFrom: TextView = itemView.findViewById(R.id.from_time)
        val txvToTime: TextView = itemView.findViewById(R.id.to_time)
        val txvDate: TextView = itemView.findViewById(R.id.date)
        val txvPurpose: TextView = itemView.findViewById(R.id.purpose)
        val btnCancel: Button = itemView.findViewById(R.id.btnCancel)
		var dashboard: Dashboard? = null

		//override fun toString(): String {
		//	return """${super.toString()} '${txvBuilding.text}'"""
		//}
	}

}
