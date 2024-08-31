package com.example.rickandmorty.core.remote

import com.example.rickandmorty.core.remote.dto.CharacterDetailDTO
import com.example.rickandmorty.core.remote.dto.CharacterEpisodeDetailDTO
import com.example.rickandmorty.core.remote.dto.CharacterListingDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface RemoteDataSource {

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com"
    }

    @GET("/api/character")
    suspend fun getCharacterListing(): Response<CharacterListingDTO>

    @GET
    suspend fun getPaginationCharacterListing(@Url url: String ): Response<CharacterListingDTO>

    @GET("/api/character/{id}")
    suspend fun getCharacterDetail(
        @Path("id") id: String
    ): Response<CharacterDetailDTO>

    @GET
    suspend fun getCharacterEpisodeDetail(@Url url: String ): Response<CharacterEpisodeDetailDTO>


}