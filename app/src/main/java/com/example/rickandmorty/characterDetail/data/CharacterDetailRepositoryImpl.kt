package com.example.rickandmorty.characterDetail.data

import com.example.rickandmorty.characterDetail.domain.model.CharacterAppearanceModel
import com.example.rickandmorty.characterDetail.domain.model.CharacterDetailModel
import com.example.rickandmorty.characterDetail.domain.repository.CharacterDetailRepository
import com.example.rickandmorty.common.ResponseState
import com.example.rickandmorty.core.customException.NoInternetException
import com.example.rickandmorty.core.remote.RemoteDataSource
import com.example.rickandmorty.core.utils.NetworkResponseDelegateImpl
import com.example.rickandmorty.core.utils.ResponseDelegate
import javax.inject.Inject

class CharacterDetailRepositoryImpl @Inject constructor(
    private val dataSource: RemoteDataSource
): CharacterDetailRepository, ResponseDelegate by NetworkResponseDelegateImpl()  {

    override suspend fun getCharacterDetail(id: String): ResponseState<CharacterDetailModel?> {
        val result = resolveResponse { dataSource.getCharacterDetail(id) }
        return if(result.isSuccess){
            ResponseState.Success(data = result.getOrNull()?.toCharacterDetail())
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

    override suspend fun getCharacterEpisodeDetail(url: String): ResponseState<CharacterAppearanceModel?> {
        val result = resolveResponse { dataSource.getCharacterEpisodeDetail(url) }
        return if(result.isSuccess){
            ResponseState.Success(data = result.getOrNull()?.toCharacterAppearanceModel())
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