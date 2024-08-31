package com.example.rickandmorty.characterDetail.data

import com.example.rickandmorty.characterDetail.domain.model.CharacterAppearanceModel
import com.example.rickandmorty.characterDetail.domain.model.CharacterDetailModel
import com.example.rickandmorty.characterListing.domain.model.CharacterStatus
import com.example.rickandmorty.characterListing.domain.model.Gender
import com.example.rickandmorty.core.remote.dto.CharacterDetailDTO
import com.example.rickandmorty.core.remote.dto.CharacterEpisodeDetailDTO

fun CharacterDetailDTO.toCharacterDetail(): CharacterDetailModel{
    return CharacterDetailModel(
        id = this.id,
        name = this.name,
        species = this.species,
        gender = Gender.fromString(this.gender),
        status = CharacterStatus.fromString(this.status),
        episodes = this.episode,
        imageUrl = this.image
    )
}

fun CharacterEpisodeDetailDTO.toCharacterAppearanceModel(): CharacterAppearanceModel{
    return CharacterAppearanceModel(
        name = this.name,
        airDate = this.air_date,
        episode = this.episode
    )
}