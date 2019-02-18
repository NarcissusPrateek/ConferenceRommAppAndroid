package com.example.conferencerommapp.Activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.conferencerommapp.R
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class UserInputActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var timeFormat = SimpleDateFormat("HH:mm ", Locale.CHINA)
        var dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        var date_text: EditText =findViewById(R.id.date)
        var fromtime : EditText =findViewById(R.id.fromTime)
        var totime: EditText = findViewById(R.id.toTime)
        var capcity: EditText = findViewById(R.id.capcity)
        var building_avtivity_button: Button = findViewById(R.id.next)


        fromtime.setOnClickListener {
            val now = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)

                var nowtime = timeFormat.format(selectedTime.time).toString()
                fromtime.text = Editable.Factory.getInstance().newEditable(nowtime)
            },now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),true)
            timePickerDialog.show()
        }



        totime.setOnClickListener {
            val now= Calendar.getInstance()
            val timePickerDialog= TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime= Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)

                var nowtime=timeFormat.format(selectedTime.time).toString()
                totime.text= Editable.Factory.getInstance().newEditable(nowtime)
            },now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),true)
            timePickerDialog.show()
        }


        date_text.setOnClickListener {
            val now= Calendar.getInstance()
            val datePicker= DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate= Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)

                var nowDate:String = dateFormat.format(selectedDate.time).toString()
                date_text.text = Editable.Factory.getInstance().newEditable(nowDate)
            },now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()

        }



        building_avtivity_button.setOnClickListener {
           if(TextUtils.isEmpty(date_text.text) ){
                Toast.makeText(applicationContext, "Please enter the Date", Toast.LENGTH_SHORT).show()

            }
            else if ( TextUtils.isEmpty(totime.text)){
                Toast.makeText(applicationContext, "Please enter the To Time", Toast.LENGTH_SHORT).show()
            }
            else if ( TextUtils.isEmpty(fromtime.text)){
                Toast.makeText(applicationContext, "Please enter the From Time", Toast.LENGTH_SHORT).show()
            }
            else if (TextUtils.isEmpty(capcity.text) ){
                Toast.makeText(applicationContext, "Please enter the Capacity", Toast.LENGTH_SHORT).show()
            }
            else {

               val buildingintent = Intent(this@UserInputActivity, BuildingsActivity::class.java)

                buildingintent.putExtra("FromTime", fromtime.text.toString())
                buildingintent.putExtra("ToTime", totime.text.toString())
                buildingintent.putExtra("Date",date_text.text.toString())
                buildingintent.putExtra("Capacity",capcity.text.toString())
                startActivity(buildingintent)
           }


        }

    }


}
