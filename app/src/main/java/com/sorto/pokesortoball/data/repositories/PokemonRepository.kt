package com.sorto.pokesortoball.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.sorto.pokesortoball.api.PokemonApi
import com.sorto.pokesortoball.data.datasource.PokemonDataSource
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class PokemonRepository @Inject constructor(private val pokemonApi: PokemonApi) : BaseRepository() {


    fun getPokemon(searchString: String?) = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 25),
        pagingSourceFactory = {
            PokemonDataSource(pokemonApi, searchString)
        }
    ).flow

    suspend fun getSinglePokemon(id: Int) = safeApiCall {
        pokemonApi.getSinglePokemon(id)

    }


}