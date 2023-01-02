package com.sorto.pokesortoball

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.sorto.pokesortoball.databinding.ActivityTeamsListBinding
import com.sorto.pokesortoball.utils.Team

class TeamsListActivity : AppCompatActivity() {

    private val database = Firebase.database
    private lateinit var messagesListener: ValueEventListener
    private lateinit var binding: ActivityTeamsListBinding
    private val listTeams:MutableList<Team> = ArrayList()
    val myRef = database.getReference("teams")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newFloatingActionButton.setOnClickListener { v ->
            val intent = Intent(this, AddTeamActivity::class.java)
            v.context.startActivity(intent)
        }

        listTeams.clear()
        setupRecyclerView(binding.teamsRecyclerView)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {

        messagesListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listTeams.clear()
                dataSnapshot.children.forEach { child ->
                    val team: Team? =
                        Team(child.child("name").getValue<String>(),
                            child.child("number").getValue<String>(),
                            child.child("description").getValue<String>(),
                            child.child("url").getValue<String>(),
                            child.key)
                    team?.let { listTeams.add(it) }
                }
                recyclerView.adapter = TeamsViewAdapter(listTeams)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "messages:onCancelled: ${error.message}")
            }
        }
        myRef.addValueEventListener(messagesListener)

        deleteSwipe(recyclerView)
    }

    class TeamsViewAdapter(private val values: List<Team>) :
        RecyclerView.Adapter<TeamsViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.team_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val team = values[position]
            holder.mNameTextView.text = team.name
            holder.mDateTextView.text = team.region
            holder.mPosterImgeView?.let {
                Glide.with(holder.itemView.context)
                    .load(team.url)
                    .into(it)
            }

            holder.itemView.setOnClickListener { v ->
                val intent = Intent(v.context, TeamDetail::class.java).apply {
                    putExtra("key", team.key)
                }
                v.context.startActivity(intent)
            }

            holder.itemView.setOnLongClickListener{ v ->
                val intent = Intent(v.context, EditTeamActivity::class.java).apply {
                    putExtra("key", team.key)
                }
                v.context.startActivity(intent)
                true
            }

        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val mNameTextView: TextView = view.findViewById(R.id.nameTextView)
            val mDateTextView: TextView = view.findViewById(R.id.dateTextView)
            val mPosterImgeView: ImageView? = view.findViewById(R.id.posterImgeView)
        }
    }

    private fun deleteSwipe(recyclerView: RecyclerView){
        val touchHelperCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                listTeams.get(viewHolder.adapterPosition).key?.let { myRef.child(it).setValue(null) }
                listTeams.removeAt(viewHolder.adapterPosition)
                recyclerView.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }
        val itemTouchHelper = ItemTouchHelper(touchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}