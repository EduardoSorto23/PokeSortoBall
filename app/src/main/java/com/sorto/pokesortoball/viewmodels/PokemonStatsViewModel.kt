package com.sorto.pokesortoball.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.sorto.pokesortoball.data.repositories.PokemonRepository
import com.sorto.pokesortoball.utils.NetworkResource
import com.sorto.pokesortoball.utils.extractId
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@HiltViewModel
class PokemonStatsViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) :
    ViewModel() {

    suspend fun getSinglePokemon(url: String) = flow {
        val id = url.extractId()
        emit(NetworkResource.Loading)
        emit(pokemonRepository.getSinglePokemon(id))
    }

}