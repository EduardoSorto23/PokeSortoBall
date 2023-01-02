package com.sorto.pokesortoball

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sorto.pokesortoball.databinding.ActivityPokedexBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokedexActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokedexBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityPokedexBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}