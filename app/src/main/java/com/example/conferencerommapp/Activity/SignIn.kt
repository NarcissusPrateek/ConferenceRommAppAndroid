package com.example.conferencerommapp

import android.content.Intent
import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.conferencerommapp.Activity.UserInputActivity
import com.example.conferencerommapp.R
import com.example.conferencerommapp.services.ConferenceService
import com.example.conferencerommapp.services.Servicebuilder11
import com.example.globofly.services.Servicebuilder
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignIn : AppCompatActivity() {

    var RC_SIGN_IN = 0
    var signInButton: SignInButton? = null
    var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        signInButton = findViewById(R.id.sign_in_button)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        signInButton!!.setOnClickListener(View.OnClickListener {
            val signInIntent = mGoogleSignInClient!!.getSignInIntent()
            startActivityForResult(signInIntent, RC_SIGN_IN)
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
            //startActivity(Intent(applicationContext, SignOut::class.java))
            finish()
        } catch (e: ApiException) {
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.statusCode)
            Toast.makeText(this@SignIn, "Failed", Toast.LENGTH_LONG).show()
        }

    }
    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {

            connectTOBackend(account.email)
            ///startActivity(Intent(applicationContext, SignOut::class.java))
            finish()
            //goAction(code)
        }
    }
    fun goAction(code: Int?) {
        Log.i("--------------", "heyeye ${code}")
        when(code) {
            11 -> {
                startActivity(Intent(this@SignIn, HRActivity::class.java))
                finish()
            }
            10 -> {
                startActivity(Intent(this@SignIn, UserInputActivity::class.java))
                finish()
            }
            0 -> {
                startActivity(Intent(this@SignIn, RegistrationActivity::class.java))
                finish()
            }
            2 -> {
                startActivity(Intent(this@SignIn, UserInputActivity::class.java))
                finish()
            }
            else -> {
                Toast.makeText(applicationContext,"Something went Wrong",Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
    fun connectTOBackend(email:String?) {

        val service = Servicebuilder11.buildService(ConferenceService::class.java)
        val requestCall : Call<Int> = service.getRequestCode(email)
        requestCall.enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.i("------helper-----",t.message)
            }
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                var code = response.body()
                Log.i("------helper on Response-----", "${code}")
                goAction(code)
            }
        })

    }
}
