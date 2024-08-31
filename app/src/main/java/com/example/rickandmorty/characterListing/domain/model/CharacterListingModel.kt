package com.example.rickandmorty.characterListing.domain.model

data class CharacterListingModel(
    val paginationData: PaginationModel,
    val characters: List<CharacterModel>
)
