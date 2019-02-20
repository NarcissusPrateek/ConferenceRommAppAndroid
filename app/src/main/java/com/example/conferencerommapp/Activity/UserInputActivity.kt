package com.example.conferencerommapp.Activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.conferencerommapp.R
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_user_inputs.*
import java.text.SimpleDateFormat
import java.util.*






class UserInputActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_inputs)


        var timeFormat = SimpleDateFormat("HH:mm ", Locale.CHINA)
        var dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        var capacity = 0
        var date_text: EditText =findViewById(R.id.date)
        var fromtime : EditText =findViewById(R.id.fromTime)
        var totime: EditText = findViewById(R.id.toTime)
        //var capcity: EditText = findViewById(R.id.capacity)
        var building_avtivity_button: Button = findViewById(R.id.next)

        fromtime.setFocusable(false)
        totime.setFocusable(false)
        date_text.setFocusable(false)
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
                //
                val selectedDate= Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)

                var nowDate:String = dateFormat.format(selectedDate.time).toString()
                date_text.text = Editable.Factory.getInstance().newEditable(nowDate)
            },now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePicker.show()

        }

//        var options = arrayOf(2,4,6,8,10,12,14,16)
//        spinner2.adapter = ArrayAdapter<Int>(this,android.R.layout.simple_list_item_1,options)
//        spinner2.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                capacity = 2
//            }
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                capacity = options.get(position)
//            }
//        }


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
            else {

               val startTime = fromTime.text.toString()
               val endTime = totime.text.toString()
               try {
                   var max1 = 2
                   if(max1 >= 2) {

                   }
                   val sdf = SimpleDateFormat("HH:mm")
                   val d1 = sdf.parse(startTime)
                   val d2 = sdf.parse(endTime)
                   val elapsed = d2.time - d1.time
                   var min : Long = 900000
                   var max: Long = 14400000
                   if((min <= elapsed) && (max >= elapsed)) {
                       Toast.makeText(this@UserInputActivity,"all Details are correct Good To GO.....",Toast.LENGTH_LONG).show()
                       val buildingintent = Intent(this@UserInputActivity, BuildingsActivity::class.java)
                       buildingintent.putExtra("FromTime", fromtime.text.toString())
                       buildingintent.putExtra("ToTime", totime.text.toString())
                       buildingintent.putExtra("Date",date_text.text.toString())
                       buildingintent.putExtra("Capacity",capacity)
                       startActivity(buildingintent)
                   }
                   else {
                       Toast.makeText(this@UserInputActivity,"From-time Must be greater than To-Time and houes must be less than 4",Toast.LENGTH_LONG).show()
                   }
               } catch (e: Exception) {
                   Toast.makeText(this@UserInputActivity,"Details are Invalid!!!",Toast.LENGTH_LONG).show()
               }

           }


        }

    }


}
