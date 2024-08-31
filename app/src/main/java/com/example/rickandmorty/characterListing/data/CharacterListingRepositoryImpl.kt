package com.example.rickandmorty.characterListing.data

import com.example.rickandmorty.characterListing.domain.model.CharacterListingModel
import com.example.rickandmorty.characterListing.domain.repository.CharacterListingRepository
import com.example.rickandmorty.common.ResponseState
import com.example.rickandmorty.core.customException.NoInternetException
import com.example.rickandmorty.core.remote.RemoteDataSource
import com.example.rickandmorty.core.utils.NetworkResponseDelegateImpl
import com.example.rickandmorty.core.utils.ResponseDelegate
import javax.inject.Inject

class CharacterListingRepositoryImpl @Inject constructor(
    private val dataSource: RemoteDataSource
): CharacterListingRepository, ResponseDelegate by NetworkResponseDelegateImpl() {


    override suspend fun getCharacterListing(): ResponseState<CharacterListingModel?> {
        val result = resolveResponse { dataSource.getCharacterListing() }
        return if(result.isSuccess){
            ResponseState.Success(data = result.getOrNull()?.toCharacterListingModel())
        }else{
            return when(result.exceptionOrNull()){
                is NoInternetException -> {
                    ResponseState.NoInternet()
                }
                else -> {
                    ResponseState.Error(message = "Something went wrong")
                }
            }
        }
    }

    override suspend fun getPaginationCharacterListing(url: String): ResponseState<CharacterListingModel?> {
        val result = resolveResponse { dataSource.getPaginationCharacterListing(url) }
        return if(result.isSuccess){
            ResponseState.Success(data = result.getOrNull()?.toCharacterListingModel())
        }else{
            return when(result.exceptionOrNull()){
                is NoInternetException -> {
                    ResponseState.NoInternet()
                }
                else -> {
                    ResponseState.Error(message = "Something went wrong")
                }
            }
        }
    }
}