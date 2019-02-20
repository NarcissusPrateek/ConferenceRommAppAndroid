package com.example.conferencerommapp.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.conferencerommapp.Helper.BuildingAdapter
import com.example.conferencerommapp.Helper.DashBoardAdapter
import com.example.conferencerommapp.Model.Building
import com.example.conferencerommapp.Model.Dashboard
import com.example.conferencerommapp.R
import com.example.conferencerommapp.SignIn
import com.example.conferencerommapp.services.ConferenceService
import com.example.globofly.services.Servicebuilder
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.github.clans.fab.*
//import kotlinx.android.synthetic.main.activity_building_list.*
import kotlinx.android.synthetic.main.activity_dashboard.*
//import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DashBoardActivity : AppCompatActivity() {

    var mGoogleSignInClient: GoogleSignInClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val userinputs: FloatingActionButton = findViewById(R.id.userInput)
        userinputs.setOnClickListener {
            startActivity(Intent(this@DashBoardActivity, UserInputActivity::class.java))
            finish()
        }
        loadDashBoard()
     }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var id = item!!.itemId
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        when(id) {
            R.id.profile -> {
                var acct = GoogleSignIn.getLastSignedInAccount(this)

            }
            R.id.log_out -> {
                    mGoogleSignInClient!!.signOut()
                    .addOnCompleteListener(this) {
                        Toast.makeText(applicationContext, "Successfully signed out", Toast.LENGTH_LONG).show()
                        startActivity(Intent(applicationContext, SignIn::class.java))
                        finish()
                    }
            }
        }

        return true
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
        val requestCall : Call<List<Dashboard>> = conferenceService.getDashboard(email)
        requestCall.enqueue(object: Callback<List<Dashboard>> {
            override fun onFailure(call: Call<List<Dashboard>>, t: Throwable) {
                Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Dashboard>>, response: Response<List<Dashboard>>) {
                Log.i("---------@_@_@_@_@--", response.body().toString())
                if(response.isSuccessful) {
                    val dashboardItemList: List<Dashboard>? = response.body()
                    if(dashboardItemList!!.isEmpty()) {

                        //val builder = AlertDialog.Builder(this@DashBoardActivity)
                        //builder.setTitle("Booking Details")
                        //builder.setMessage("No Booking Found!!!")
                        //builder.show()
                    }
                    else {
                        dashbord_recyclerview.adapter = DashBoardAdapter(dashboardItemList!!)
                    }

                }
                else {
                    Toast.makeText(applicationContext,"Unable to Load Booking Details. Please try again",Toast.LENGTH_LONG).show()
                }

            }

        })
    }
}