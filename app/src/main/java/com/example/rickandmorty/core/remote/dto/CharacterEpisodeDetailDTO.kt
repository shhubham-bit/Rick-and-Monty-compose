package com.example.rickandmorty.core.remote.dto

data class CharacterEpisodeDetailDTO(
    val air_date: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)