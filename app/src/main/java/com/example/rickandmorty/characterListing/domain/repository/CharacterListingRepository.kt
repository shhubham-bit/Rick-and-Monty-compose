package com.example.rickandmorty.characterListing.domain.repository

import com.example.rickandmorty.characterListing.domain.model.CharacterListingModel
import com.example.rickandmorty.common.ResponseState

interface CharacterListingRepository {

    suspend fun getCharacterListing(): ResponseState<CharacterListingModel?>
    suspend fun getPaginationCharacterListing(url: String): ResponseState<CharacterListingModel?>
}