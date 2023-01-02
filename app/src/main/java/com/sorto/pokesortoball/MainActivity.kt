package com.sorto.pokesortoball

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.sorto.pokesortoball.databinding.ActivityMainBinding
import com.sorto.pokesortoball.utils.SavedPreference
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val txtUserName: TextView = findViewById<TextView>(R.id.txtUserName)

        txtUserName.text = SavedPreference.getUsername(this)

        if(SavedPreference.getUsername(this) !=null){
            Glide.with(this)
                .load(SavedPreference.getPhoto(this))
                .placeholder(R.mipmap.profile)
                .into(findViewById(R.id.imgPhotoProfile))
        }

        binding.imgTeams.setOnClickListener { view: View ->
            val intent = Intent(this, TeamsListActivity::class.java)
            startActivity(intent)
        }

        binding.imgPokedex.setOnClickListener { view: View ->
            val intent = Intent(this, PokedexActivity::class.java)
            startActivity(intent)
        }

        binding.imgSettings.setOnClickListener {  view: View ->
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent) }

    }

    private  fun printKeyHash(){
        try {
            val info  = packageManager.getPackageInfo("com.sorto.pokesortoball",PackageManager.GET_SIGNATURES)
            for(signature in info.signatures){
                val md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray())
                Log.e("KEYHASH",Base64.encodeToString(md.digest(),Base64.DEFAULT))
            }
        }
        catch (e:PackageManager.NameNotFoundException){

        }
    }
}