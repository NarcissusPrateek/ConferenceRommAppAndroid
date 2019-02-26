package com.example.conferencerommapp

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.conferencerommapp.Activity.DashBoardActivity
import com.example.conferencerommapp.services.ConferenceService
import com.example.globofly.services.Servicebuilder
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignIn : AppCompatActivity() {

    var progressDialog: ProgressDialog? = null
    var RC_SIGN_IN = 0
    var signInButton: SignInButton? = null
    var mGoogleSignInClient: GoogleSignInClient? = null
    var prefs: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin_new)
        prefs = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        signInButton = findViewById(R.id.sign_in_button)
        //image_login.setImageResource(R.drawable.profile)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        signInButton!!.setOnClickListener(View.OnClickListener {
            val signInIntent = mGoogleSignInClient!!.getSignInIntent()
            startActivityForResult(signInIntent, RC_SIGN_IN)

            //finish()
        })

    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
           val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            connectTOBackend(account.email)
            //finish()
        } catch (e: ApiException) {
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.statusCode)
            Toast.makeText(this@SignIn, "Failed", Toast.LENGTH_LONG).show()
        }

    }

    override fun onStart() {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            prefs = getSharedPreferences("myPref", Context.MODE_PRIVATE)
            connectTOBackend(account.email)
            //finish()
        }
        super.onStart()

    }
    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }
    fun goAction(code: Int?) {
        when(code) {
            11 -> {
                startActivity(Intent(this@SignIn, BlockedDashboard::class.java))
                //finish()
            }
            10 -> {
                startActivity(Intent(this@SignIn, DashBoardActivity::class.java))
               // finish()
            }
            0 -> {
                startActivity(Intent(this@SignIn, RegistrationActivity::class.java))
               // finish()
            }
            2 -> {
                startActivity(Intent(this@SignIn, DashBoardActivity::class.java))
                //finish()
            }
            else -> {
                val builder = AlertDialog.Builder(this@SignIn)
                builder.setTitle("Error!")
                builder.setMessage("Plese Restart the application..")
                builder.setPositiveButton("Ok"){dialog, which ->
                    finish()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }

        }
    }
    fun connectTOBackend(email:String?) {
        val service = Servicebuilder.buildService(ConferenceService::class.java)
        val requestCall : Call<Int> = service.getRequestCode(email)
        requestCall.enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                //Handler().postDelayed({progressDialog!!.dismiss()},5000)
                //Log.i("------helper-----",t.message)
                Toast.makeText(this@SignIn, "Server not found!! Please try again after some time",Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if(response.isSuccessful) {
                //   progressDialog!!.dismiss()
                    var code = response.body()
                    val  editor = prefs!!.edit()
                    editor.putInt("Code",code!!)
                    editor.apply()
                    goAction(code)
                    //Toast.makeText(this@SignIn,"code is ${getSharedPreferences("myPref", Context.MODE_PRIVATE).getInt("Code",5)}",Toast.LENGTH_LONG).show()
                }else {
                  //  progressDialog!!.dismiss()
                }

            }
        })

    }
}
