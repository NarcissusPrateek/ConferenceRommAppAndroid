package com.example.conferencerommapp

import android.content.Intent
import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.conferencerommapp.Model.Employee
import com.example.conferencerommapp.R
import com.example.conferencerommapp.services.ConferenceService
import com.example.conferencerommapp.services.Servicebuilder11
import com.example.globofly.services.Servicebuilder
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_registration.*
//import com.nineleaps.signin.Model.Employee
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {

    var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        Toast.makeText(applicationContext,"Registration Activity", Toast.LENGTH_LONG).show()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val acct = GoogleSignIn.getLastSignedInAccount(applicationContext)

        textView_name.text = acct!!.displayName
        textView_email.text = acct!!.email

        val employee = Employee()
        employee.EmployeeId = edittext_id.text.toString()
        employee.Role = edittext_Role.text.toString()
        employee.ActivationCode = "xxx"
        employee.Name = acct.displayName
        employee.Email = acct.email
        employee.Verified = false

        button_add.setOnClickListener(View.OnClickListener {
            addEmployee(employee)
        })

    }
    fun addEmployee(employee: Employee) {
            val service = Servicebuilder11.buildService(ConferenceService::class.java)
            val requestCall: Call<Int> = service.addEmployee(employee)/*employee.Email,
                                                             employee.EmployeeId,
                                                             employee.Name,
                                                             employee.Role,
                                                             employee.Verified,
                                                             employee.ActivationCode)*/
        requestCall.enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(applicationContext,"on failure in registration ${t.message}",Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if(response.isSuccessful) {
                    Toast.makeText(applicationContext,"Information added Successfully",Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@RegistrationActivity, SignOut::class.java))
                    finish()
                }
                else {
                    Toast.makeText(applicationContext,"Response is wrong ${response.body()}",Toast.LENGTH_LONG).show()
                }

            }

        })
    }
}