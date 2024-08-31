package com.example.rickandmorty.characterDetail.domain.model

import com.example.rickandmorty.characterListing.domain.model.CharacterStatus
import com.example.rickandmorty.characterListing.domain.model.Gender

data class CharacterDetailModel(
    val id: Int,
    val name: String,
    val species: String,
    val gender: Gender?,
    val status: CharacterStatus?,
    val imageUrl: String,
    val episodes: List<String>
)
