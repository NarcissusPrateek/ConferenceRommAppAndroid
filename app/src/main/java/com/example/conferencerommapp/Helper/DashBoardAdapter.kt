package com.example.conferencerommapp.Helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.conferencerommapp.Model.Dashboard
import com.example.conferencerommapp.R


class DashBoardAdapter(val dashboardItemList: List<Dashboard>) : androidx.recyclerview.widget.RecyclerView.Adapter<DashBoardAdapter.ViewHolder>() {

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
        var dashboard: Dashboard? = null

		//override fun toString(): String {
		//	return """${super.toString()} '${txvBuilding.text}'"""
		//}
	}

}
