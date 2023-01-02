package com.sorto.pokesortoball

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.sorto.pokesortoball.utils.SavedPreference

class ProfileActivity : AppCompatActivity() {
    lateinit var mGoogleSignInClient: GoogleSignInClient

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val txtUsername: TextView = findViewById<TextView>(R.id.txtUsernameProf)
        val txtEmail: TextView = findViewById<TextView>(R.id.txtEmailProf)

        txtUsername.text = SavedPreference.getUsername(this)
        txtEmail.text = SavedPreference.getEmail(this)

        if(SavedPreference.getUsername(this) !=null) {
            Glide.with(this)
                .load(SavedPreference.getPhoto(this))
                .placeholder(R.mipmap.profile)
                .into(findViewById(R.id.imgPhoto))
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)

        findViewById<TextView>(R.id.btnSingout).setOnClickListener{ view: View ->
            mGoogleSignInClient.signOut().addOnCompleteListener {
                val intent= Intent(this, LoginActivity::class.java)
                Toast.makeText(this,"Logging Out",Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            }
        }
        //btnSingout
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}