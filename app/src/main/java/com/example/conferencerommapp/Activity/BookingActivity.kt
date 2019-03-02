package com.example.conferencerommapp.Activity

import android.app.ProgressDialog
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
import kotlinx.android.synthetic.main.test.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import kotlin.text.StringBuilder


class BookingActivity: AppCompatActivity() {

    var progressDialog: ProgressDialog? = null
    var mUserItems = ArrayList<Int>()
    val EmailList = ArrayList<String>()
    var bookedStatus:Boolean=false
    var mGoogleSignInClient: GoogleSignInClient? = null
    var str :StringBuilder? = null
    var userStatus=-1
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


        button.setOnClickListener {
            var servicebuilder = Servicebuilder.buildService(ConferenceService::class.java)
            var requestCall = servicebuilder.getEmployees()
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
                        val listItems = arrayOfNulls<String>(list.size)
                        list.toArray(listItems)


                        var checkedItems: BooleanArray = BooleanArray(emplist.size)

                        val mBuilder = AlertDialog.Builder(this@BookingActivity)
                        mBuilder.setTitle("Select Members for Meeting")

                        mBuilder.setMultiChoiceItems(
                            listItems,
                            checkedItems,
                            DialogInterface.OnMultiChoiceClickListener { dialogInterface, position, isChecked ->
                                if (isChecked) {
                                    mUserItems.add(position)
                                } else if (mUserItems.contains(position)) {
                                    mUserItems.remove(position)
                                }
                            })
                        mBuilder.setCancelable(false)
                        mBuilder.setPositiveButton(
                            R.string.ok_label,
                            DialogInterface.OnClickListener { dialogInterface, which ->
                                str = StringBuilder("")
                                for (i in mUserItems.indices) {
                                    str!!.append(emplist[mUserItems.get(i)].Email!!)
                                    if(i != (mUserItems.size - 1)) {
                                        str!!.append(",")
                                    }
                                }
                                mUserItems.clear()
                            })
                        mBuilder.setNegativeButton(
                            R.string.dismiss_label,
                            DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() }
                        )
                        mBuilder.setNeutralButton(
                            R.string.clear_all_label,
                            DialogInterface.OnClickListener { dialogInterface, which ->
                                for (i in checkedItems.indices) {
                                    checkedItems[i] = false
                                }
                                str = StringBuilder("")
                                mUserItems.clear()
                                EmailList.clear()
                            })
                        val mDialog = mBuilder.create()
                        mDialog.show()
                    }

                }

            })
        }
        book_button.setOnClickListener {
            if(edittextPurpose.text.isEmpty()) {
                Toast.makeText(this@BookingActivity,"Please Enter the purpose of meeting.",Toast.LENGTH_LONG).show()
            }else {
                progressDialog = ProgressDialog(this@BookingActivity)
                progressDialog!!.setMessage("Processing....")
                progressDialog!!.setCancelable(false)
                progressDialog!!.show()
                booking.Purpose = edittextPurpose.text.toString()
                booking.CName = roomname
                booking.CCMail = str.toString()
                addBookingDetails(booking,booking.Email.toString())
            }
        }
    }


    private fun addBookingDetails(booking: Booking,email: String)  {
        val service = Servicebuilder.buildService(ConferenceService::class.java )
        val requestCall : Call<Int> = service.addBookingDetails(booking)
        requestCall.enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                progressDialog!!.dismiss()
                Toast.makeText(this@BookingActivity,"Error on Failure",Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<Int>, response: Response<Int>) { Log.i("-------#####-----",booking.Purpose)
                if(response.isSuccessful) {
                    val code = response.body()
                    bookedStatus = true
                    var code1 = getCode(email)
                    progressDialog!!.dismiss()
                    val intent=Intent(Intent(this@BookingActivity, DashBoardActivity::class.java))
                    intent.putExtra("flag",code1)
                    startActivity(intent)
                    finish()
                }
                else {
                    progressDialog!!.dismiss()
                    Toast.makeText(this@BookingActivity,"Unable to Book.... ",Toast.LENGTH_LONG).show()
                }

            }

        })
    }

    private fun getCode(email: String): Int? {
        var code1:Int? = 10
        val service = Servicebuilder.buildService(ConferenceService::class.java)
        val requestCall : Call<Int> = service.getRequestCode(email)
        requestCall.enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.i("------helper-----",t.message)
            }
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                var code = response.body()
                code1 = code
            }
        })
        return code1
    }
}