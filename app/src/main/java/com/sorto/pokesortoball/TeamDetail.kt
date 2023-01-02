package com.sorto.pokesortoball

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sorto.pokesortoball.databinding.ActivityTeamDetailBinding
import com.sorto.pokesortoball.utils.Team

class TeamDetail : AppCompatActivity() {
    private lateinit var binding: ActivityTeamDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val key = intent.getStringExtra("key")
        val database = Firebase.database
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS") val myRef = database.getReference("teams").child(key.toString())

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val team:Team? = dataSnapshot.getValue(Team::class.java)
                if (team != null) {
                    binding.nameTextView.text = team.name.toString()
                    binding.descriptionTextView.text = team.description.toString()
                    images(team.url.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })

    }

    private  fun images(url: String){
        Glide.with(this)
            .load(url)
            .into(binding.posterImgeView)

        Glide.with(this)
            .load(url)
            .into(binding.backgroundImageView)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}