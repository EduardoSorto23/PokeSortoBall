package com.sorto.pokesortoball

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sorto.pokesortoball.databinding.ActivityAddTeamBinding
import com.sorto.pokesortoball.databinding.ActivityEditTeamBinding
import com.sorto.pokesortoball.utils.Team

class EditTeamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTeamBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbars)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val key = intent.getStringExtra("key")
        val database = Firebase.database
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS") val myRef = database.getReference("teams").child(key.toString())

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val team:Team? = dataSnapshot.getValue(Team::class.java)
                if (team != null) {
                    binding.nameEditText.text = Editable.Factory.getInstance().newEditable(team.name)
                    binding.spinner.setSelection(resources.getStringArray(R.array.Regions).indexOf(team.region))
                    binding.descriptionEditText.text = Editable.Factory.getInstance().newEditable(team.description)
                    binding.urlEditText.text = Editable.Factory.getInstance().newEditable(team.url)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })


    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}