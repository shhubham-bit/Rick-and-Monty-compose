package com.example.rickandmorty.characterListing.domain.model

data class CharacterModel(
    val id: Int,
    val name: String,
    val status: CharacterStatus?,
    val species: String,
    val gender: Gender?,
    val imageUrl: String,
)
