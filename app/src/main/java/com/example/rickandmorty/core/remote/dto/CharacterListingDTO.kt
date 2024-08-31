package com.example.rickandmorty.core.remote.dto

data class CharacterListingDTO(
    val info: PaginationMetaData,
    val results: List<CharacterDTO>
)