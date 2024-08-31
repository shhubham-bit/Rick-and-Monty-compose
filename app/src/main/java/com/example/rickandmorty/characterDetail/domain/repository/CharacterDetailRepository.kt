package com.example.rickandmorty.characterDetail.domain.repository

import com.example.rickandmorty.characterDetail.domain.model.CharacterAppearanceModel
import com.example.rickandmorty.characterDetail.domain.model.CharacterDetailModel
import com.example.rickandmorty.common.ResponseState

interface CharacterDetailRepository {

    suspend fun getCharacterDetail(id: String): ResponseState<CharacterDetailModel?>
    suspend fun getCharacterEpisodeDetail(url: String): ResponseState<CharacterAppearanceModel?>
}