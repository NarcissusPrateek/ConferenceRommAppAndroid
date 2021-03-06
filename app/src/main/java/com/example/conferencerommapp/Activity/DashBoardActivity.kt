package com.example.conferencerommapp.Activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.conferencerommapp.BlockedDashboard
import com.example.conferencerommapp.Helper.DashBoardAdapter
import com.example.conferencerommapp.Model.Dashboard
import com.example.conferencerommapp.SignIn
import com.example.conferencerommapp.services.ConferenceService
import com.example.globofly.services.Servicebuilder
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.github.clans.fab.*
import kotlinx.android.synthetic.main.activity_dashboard.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.conferencerommapp.R


class DashBoardActivity : AppCompatActivity() {

    var mGoogleSignInClient: GoogleSignInClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val actionbar=supportActionBar
        actionbar!!.setTitle("Bookings")
        val userinputs: FloatingActionButton = findViewById(R.id.userInput)
        userinputs.setOnClickListener {
            startActivity(Intent(this@DashBoardActivity, UserInputActivity::class.java))
            //finish()
        }
//        var profile_image: com.mikhaellopez.circularimageview.CircularImageView = findViewById(R.id.profile_image)
//        var profile_name:TextView = findViewById(R.id.profile_name)
//        val acct = GoogleSignIn.getLastSignedInAccount(this@DashBoardActivity)
//        val name = acct!!.displayName
//        profile_name.setText("Hello, ${name}")
//        val personPhoto = acct!!.getPhotoUrl()
//        Log.i("------------------",personPhoto.toString())
//        profile_email.text = acct.email
//
//        if(personPhoto == null )
//        {
//            profile_image.setImageResource(R.drawable.profile)
//        }
//        else {
//            Glide.with(applicationContext).load(personPhoto).thumbnail(1.0f).into(profile_image)
//           }
//        loadDashBoard()

    }



    override fun onBackPressed() {
        super.onBackPressed()
        val pref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        var code = pref.getInt("Code",10)
        if(code == 11) {
            intent=Intent(Intent(this@DashBoardActivity, BlockedDashboard::class.java))
            startActivity(intent)
            finish()
        }
        else {
            finishAffinity();
        }
    }
    private fun loadDashBoard() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val acct = GoogleSignIn.getLastSignedInAccount(applicationContext)
        var email = acct!!.email
        val conferenceService = Servicebuilder.buildService(ConferenceService::class.java)
        val requestCall : Call<List<Dashboard>> = conferenceService.getDashboard(email!!)
        requestCall.enqueue(object: Callback<List<Dashboard>> {
            override fun onFailure(call: Call<List<Dashboard>>, t: Throwable) {
                Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Dashboard>>, response: Response<List<Dashboard>>) {
                if(response.isSuccessful) {
                    val dashboardItemList: List<Dashboard>? = response.body()
                    if(dashboardItemList!!.isEmpty()) {

                        Toast.makeText(applicationContext,"No further booking found...", Toast.LENGTH_LONG).show()
                    }
                    else {
                        dashbord_recyclerview1.adapter = DashBoardAdapter(dashboardItemList!!, this@DashBoardActivity)
                    }
                }
                else {
                    Toast.makeText(applicationContext,"Unable to Load Booking Details. Please try again",Toast.LENGTH_LONG).show()
                }

            }

        })
    }
}