package com.sorto.pokesortoball

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sorto.pokesortoball.databinding.ActivityAddTeamBinding
import com.sorto.pokesortoball.utils.SavedPreference
import com.sorto.pokesortoball.utils.Team

class AddTeamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTeamBinding
    private val database = Firebase.database
    private var regionSelectedPk: String ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val regions = resources.getStringArray(R.array.Regions)

        val myRef = database.getReference("teams")

        val name=binding.nameEditText.text
        val number= 1 // make it autoincrementable I am missing
        val region= SavedPreference.getRegion(this)
        val description=binding.descriptionEditText.text
        val url=binding.urlEditText.text

        binding.saveButton.setOnClickListener { v ->
            Log.e("Valor",region.toString())
            val Teams = Team(name.toString(), number.toString(), region, description.toString(), url.toString())
            myRef.child(myRef.push().key.toString()).setValue(Teams)
            finish()
        }

        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, regions)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    regionSelectedPk = regions[position]
                    val snack = Snackbar.make(view,getString(R.string.selected_item) + " " + ""+regionSelectedPk, Snackbar.LENGTH_LONG)
                    snack.show()
                    SavedPreference.setRegion(this@AddTeamActivity,regions[position])
                   /* Toast.makeText(this@MainActivity,
                        getString(R.string.selected_item) + " " +
                                "" + languages[position], Toast.LENGTH_SHORT).show()*/
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}