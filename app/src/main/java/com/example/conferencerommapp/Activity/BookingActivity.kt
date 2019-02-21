package com.example.conferencerommapp.Activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.conferencerommapp.Model.Booking
import com.example.conferencerommapp.Model.EmployeeList
import com.example.conferencerommapp.Model.ListOfEmployee
import com.example.conferencerommapp.R
import com.example.conferencerommapp.services.ConferenceService
import com.example.globofly.services.Servicebuilder
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.activity_main11111.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList




class BookingActivity: AppCompatActivity() {

    var mUserItems = ArrayList<Int>()
    var mGoogleSignInClient: GoogleSignInClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        val txv_fromTime: TextView = findViewById(R.id.textView_from_time)
        val txv_toTime: TextView = findViewById(R.id.textView_to_time)
        val txvDate: TextView = findViewById(R.id.textView_date)
        val txvConfName: TextView = findViewById(R.id.textView_conf_name)
        val txvemployeename: TextView = findViewById(com.example.conferencerommapp.R.id.textView_name)
        var edittextPurpose = findViewById(com.example.conferencerommapp.R.id.editText_purpose) as EditText
        val txvBildingname: TextView = findViewById(R.id.textView_buildingname)

        val bundle: Bundle? = intent.extras
        var fromtime = bundle!!.get("FromTime").toString()
        var totime = bundle.get("ToTime").toString()
        var date = bundle.get("Date").toString()
        var roomname = bundle.get("RoomName").toString()
        var buildingname = bundle.get("BuildingName").toString()
        var capacity = bundle.get("Capacity").toString()
        var cid = bundle.get("RoomId").toString()
        var bid = bundle.get("BuildingId").toString()


        txv_fromTime.text = fromtime
        txv_toTime.text = totime
        txvDate.text = date
        txvConfName.text = roomname
        txvBildingname.text = buildingname

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val acct = GoogleSignIn.getLastSignedInAccount(applicationContext)

        val booking = Booking()
        booking.Email = acct!!.email
        booking.CId = cid.toInt()
        booking.BId = bid.toInt()
        booking.FromTime = fromtime
        booking.ToTime = totime
        txvemployeename.text = acct.displayName






    /*    button1.setOnClickListener {
            requestCall.enqueue(object : Callback<List<EmployeeList>> {
                override fun onFailure(call: Call<List<EmployeeList>>, t: Throwable) {
                    Toast.makeText(this@BookingActivity, "On failure while Loading the EmployeeList", Toast.LENGTH_LONG)
                        .show()
                }

                override fun onResponse(call: Call<List<EmployeeList>>, response: Response<List<EmployeeList>>) {
                    if (response.isSuccessful) {
                        var emplist: List<EmployeeList>? = response.body()
                        val list = ArrayList<String>()
                        for (item: EmployeeList in emplist!!) {
                            list.add(item.Name!!)
                        }
                        val array = arrayOfNulls<String>(list.size)
                        list.toArray(array)
                        //val email_list = selectMember(array, emplist, employee)
                       // Log.i("-------@_@_## ----", employee.EmailList.toString())
                    }
                }

            })
        }*/
        book_button.setOnClickListener {
            booking.Purpose = edittextPurpose.text.toString()
            booking.CName = roomname
            addBookingDetails(booking)
       }


    }
    /*fun getEmployeeList(): List<EmployeeList> {
        var emplist = ArrayList<EmployeeList>()
        var employee = ListOfEmployee()
        var servicebuilder = Servicebuilder.buildService(ConferenceService::class.java)
        var requestCall = servicebuilder.getEmployees()
        requestCall.enqueue(object: Callback<ArrayList<EmployeeList>> {
            override fun onFailure(call: Call<ArrayList<EmployeeList>>, t: Throwable) {

            }

            override fun onResponse(call: Call<ArrayList<EmployeeList>>, response: Response<ArrayList<EmployeeList>>) {
                if(response.isSuccessful) {
                    emplist = response.body()
                }
                else {
                    Toast.makeText(this@BookingActivity, "Unable to load Employee List",Toast.LENGTH_LONG).show()
                }
            }

        })
    }*/
    fun selectMember(listItems: Array<String?>, emplist: List<EmployeeList>, employee: ListOfEmployee)/*: ArrayList<String?> */{
        Log.i("56565656","34345d4fg")
        val EmailList = ArrayList<String?>()
        var checkedItems: BooleanArray = BooleanArray(emplist.size)
        var email_list = mutableListOf<String>()
        val mBuilder = AlertDialog.Builder(this@BookingActivity)
        mBuilder.setTitle("Select Members for Meeting")

        mBuilder.setMultiChoiceItems(
            listItems,
            checkedItems,
            DialogInterface.OnMultiChoiceClickListener { dialogInterface, position, isChecked ->
                if (isChecked) {
                    mUserItems.add(position)
                } else {
                    mUserItems.remove(Integer.valueOf(position))
                }
            })
        mBuilder.setCancelable(false)
        mBuilder.setPositiveButton(R.string.ok_label, DialogInterface.OnClickListener { dialogInterface, which ->
            // var employee  = ListOfEmployee()
            for (i in mUserItems.indices) {
                Log.i("---------", emplist[i].Email)
                //EmailList.add(emplist[i].Email)
                employee.EmailList!!.add(emplist[i].Email!!)
                //email_list.add(emplist.get(i).Email!!)
            }

            Log.i("----%%%%%555-----", employee.EmailList.toString())
        })
        mBuilder.setNegativeButton(
            R.string.dismiss_label,
            DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss()}

        )
        mBuilder.setNeutralButton(
            R.string.clear_all_label,
            DialogInterface.OnClickListener { dialogInterface, which ->
                for (i in checkedItems.indices) {
                    checkedItems[i] = false
                    mUserItems.clear()
                    //employee = null
                    //EmailList.clear()
                }
            })
        val mDialog = mBuilder.create()
        mDialog.show()
        //   return EmailList
    }
    private fun addBookingDetails(booking: Booking)  {
        val service = Servicebuilder.buildService(ConferenceService::class.java )
        val requestCall : Call<Int> = service.addBookingDetails(booking)
        requestCall.enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(this@BookingActivity,"Error on Failure",Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<Int>, response: Response<Int>) { Log.i("-------#####-----",booking.Purpose)
                if(response.isSuccessful) {
                    val code = response.body()
                    Toast.makeText(this@BookingActivity,"Successully Booked with code ${code}",Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@BookingActivity, DashBoardActivity::class.java))
                    finish()
                }
                else {
                    Toast.makeText(this@BookingActivity,"Response Error",Toast.LENGTH_LONG).show()
                }

            }

        })
    }
}