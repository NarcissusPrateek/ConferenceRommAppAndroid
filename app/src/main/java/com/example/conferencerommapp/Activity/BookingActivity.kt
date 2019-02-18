package com.example.conferencerommapp.Activity

import android.app.AppComponentFactory
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.conferencerommapp.Model.Booking
import com.example.conferencerommapp.R
import com.example.conferencerommapp.services.ConferenceService
import com.example.globofly.services.Servicebuilder
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.blocked_list.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class BookingActivity: AppCompatActivity() {

    var mGoogleSignInClient: GoogleSignInClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)



        //Toast.makeText(applicationContext, "Hey It's Booking Activity",Toast.LENGTH_LONG).show()

        val txv_fromTime : TextView = findViewById(R.id.textView_from_time)
        val txv_toTime : TextView = findViewById(R.id.textView_to_time)
        val txvDate : TextView = findViewById(R.id.textView_date)
        val txvConfName : TextView = findViewById(R.id.textView_conf_name)
        val txvemployeename: TextView = findViewById(R.id.textView_name)
        var edittextPurpose  = findViewById(R.id.editText_purpose) as EditText
         val txvBildingname : TextView = findViewById(R.id.textView_buildingname)

        val bundle: Bundle? = intent.extras
        var fromtime  = bundle!!.get("FromTime").toString()
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

        val booking =  Booking()
        booking.Email = acct!!.email
        booking.CId = cid.toInt()
        booking.BId = bid.toInt()
        booking.FromTime = fromtime
        booking.ToTime = totime
       
        txvemployeename.text = acct.displayName
        book_button.setOnClickListener {
            booking.Purpose = edittextPurpose.text.toString()
            addBookingDetails(booking)
       }
    }
    private fun addBookingDetails(booking: Booking)  {
        val service = Servicebuilder.buildService(ConferenceService::class.java )
        val requestCall : Call<Int> = service.addBookingDetails(booking)
        requestCall.enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(this@BookingActivity,"Error on Failure",Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                Log.i("-------#####-----",booking.Purpose)
                if(response.isSuccessful) {
                    val code = response.body()
                    Toast.makeText(this@BookingActivity,"Successully Booked with code ${code}",Toast.LENGTH_LONG).show()
                }
                else {
                    Toast.makeText(this@BookingActivity,"Response Error",Toast.LENGTH_LONG).show()
                }

            }

        })
    }
}