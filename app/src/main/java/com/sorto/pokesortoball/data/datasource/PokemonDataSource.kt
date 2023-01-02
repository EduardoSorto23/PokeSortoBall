package com.sorto.pokesortoball.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sorto.pokesortoball.api.PokemonApi
import com.sorto.pokesortoball.model.PokemonResult
import com.sorto.pokesortoball.utils.SEARCH_LOAD_SIZE
import com.sorto.pokesortoball.utils.STARTING_OFFSET_INDEX
import java.io.IOException



class PokemonDataSource(private val pokemonApi: PokemonApi, private val searchString: String?) :
    PagingSource<Int, PokemonResult>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResult> {
        val offset = params.key ?: STARTING_OFFSET_INDEX


        val loadSize = if (searchString == null) params.loadSize else SEARCH_LOAD_SIZE
        return try {
            val data = pokemonApi.getPokemons(loadSize, offset)

            //if search string isn't null filter the pokemon based on what user searched.
            val filteredData = if (searchString != null) {
                data.results.filter { it.name.contains(searchString, true) }
            } else {
                data.results
            }


            LoadResult.Page(
                data = filteredData,
                prevKey = if (offset == STARTING_OFFSET_INDEX) null else offset - loadSize,
                nextKey = if (data.next == null) null else offset + loadSize
            )
        } catch (t: Throwable) {
            var exception = t

            if (t is IOException) {
                exception = IOException("Please check internet connection")
            }
            LoadResult.Error(exception)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, PokemonResult>): Int? {

        return state.anchorPosition

    }
}