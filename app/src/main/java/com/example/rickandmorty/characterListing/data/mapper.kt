package com.example.rickandmorty.characterListing.data

import com.example.rickandmorty.characterListing.domain.model.CharacterListingModel
import com.example.rickandmorty.characterListing.domain.model.CharacterModel
import com.example.rickandmorty.characterListing.domain.model.CharacterStatus
import com.example.rickandmorty.characterListing.domain.model.Gender
import com.example.rickandmorty.characterListing.domain.model.PaginationModel
import com.example.rickandmorty.core.remote.dto.CharacterDTO
import com.example.rickandmorty.core.remote.dto.CharacterListingDTO
import com.example.rickandmorty.core.remote.dto.PaginationMetaData

fun CharacterListingDTO.toCharacterListingModel(): CharacterListingModel{
    return CharacterListingModel(
        paginationData = this.info.toPaginationModel(),
        characters = this.results.map { it.toCharacterModel() }
    )
}

fun PaginationMetaData.toPaginationModel(): PaginationModel{
    return PaginationModel(
        count = this.count, pages = this.pages, next = this.next, prev = this.prev
    )
}

fun CharacterDTO.toCharacterModel(): CharacterModel{
    return  CharacterModel(
        id = this.id,
        name = this.name,
        gender = Gender.fromString(this.gender),
        status = CharacterStatus.fromString(this.status),
        imageUrl = this.image,
        species = this.species
    )
}

