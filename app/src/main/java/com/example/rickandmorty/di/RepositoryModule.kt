package com.example.rickandmorty.di

import com.example.rickandmorty.characterDetail.data.CharacterDetailRepositoryImpl
import com.example.rickandmorty.characterDetail.domain.repository.CharacterDetailRepository
import com.example.rickandmorty.characterListing.data.CharacterListingRepositoryImpl
import com.example.rickandmorty.characterListing.domain.repository.CharacterListingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCharacterListingRepository(
        characterListingRepositoryImpl: CharacterListingRepositoryImpl
    ): CharacterListingRepository

    @Binds
    abstract fun bindCharacterDetailRepository(
        characterDetailRepositoryImpl: CharacterDetailRepositoryImpl
    ): CharacterDetailRepository
}