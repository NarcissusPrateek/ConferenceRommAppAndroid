package com.example.conferencerommapp

import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.content.Intent
import android.widget.Toast
//import android.support.annotation.NonNull
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.conferencerommapp.R


class SignOut : AppCompatActivity() {

    var mGoogleSignInClient: GoogleSignInClient? = null
    var sign_out: Button? = null
    var nameTV: TextView? = null
    var emailTV: TextView? = null
    var idTV: TextView? = null
    var photoIV: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        sign_out = findViewById(R.id.log_out)
        nameTV = findViewById(R.id.name)
        emailTV = findViewById(R.id.email)
        idTV = findViewById(R.id.id)
        photoIV = findViewById(R.id.photo)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val acct = GoogleSignIn.getLastSignedInAccount(applicationContext)
        if (acct != null) {
            val personName = acct.displayName
            val personGivenName = acct.givenName
            val personFamilyName = acct.familyName
            val personEmail = acct.email
            val personId = acct.id
            val personPhoto = acct.photoUrl


            nameTV!!.text = personName
            emailTV!!.text = personEmail
            idTV!!.text = personId
        }

        sign_out!!.setOnClickListener(View.OnClickListener {
            mGoogleSignInClient!!.signOut()
                .addOnCompleteListener(this) {
                    Toast.makeText(applicationContext, "Successfully signed out", Toast.LENGTH_LONG).show()
                    startActivity(Intent(applicationContext, SignIn::class.java))
                    finish()
                }
        })
    }

    private fun signOut() {

    }

}