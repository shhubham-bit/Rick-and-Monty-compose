package com.example.rickandmorty.characterListing.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.characterListing.domain.model.CharacterModel
import com.example.rickandmorty.characterListing.domain.repository.CharacterListingRepository
import com.example.rickandmorty.common.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListingViewModel @Inject constructor(
    private val characterListingRepository: CharacterListingRepository
) : ViewModel(){

    private val _uiState: MutableStateFlow<CharacterListingUIState> = MutableStateFlow(CharacterListingUIState())
    val uiState = _uiState.asStateFlow()


    fun getCharacterListing(){
        launchInViewModelScope {
            _uiState.emit(uiState.value.copy(
                isContentLoading = true
            ))
            val response = characterListingRepository.getCharacterListing()
            when(response){
                is ResponseState.Success -> {
                    _uiState.emit(uiState.value.copy(
                        isContentLoading = false,
                        characters = response.data?.characters ?: listOf(),
                        next = response.data?.paginationData?.next
                    ))
                }
                is ResponseState.Error -> {

                }
                is ResponseState.NoInternet -> {

                }
            }
        }
    }

    fun getPaginationCharacterListing(){
        if(uiState.value.next == null){
            return
        }
        launchInViewModelScope {
            _uiState.emit(uiState.value.copy(
                isPaginationInProgress = true
            ))
            val response = characterListingRepository.getPaginationCharacterListing(uiState.value.next!!)
            when(response){
                is ResponseState.Success -> {
                    val characters: MutableList<CharacterModel> = mutableListOf()
                    characters.addAll(uiState.value.characters)
                    response.data?.characters?.let { characters.addAll(it) }
                    _uiState.emit(uiState.value.copy(
                        characters = characters,
                        next = response.data?.paginationData?.next,
                        isPaginationInProgress = false
                        ))
                }
                is ResponseState.Error -> {

                }
                is ResponseState.NoInternet -> {

                }
            }
        }
    }

    private fun launchInViewModelScope(action: suspend () -> Unit){
        viewModelScope.launch {
            action()
        }
    }

}

data class CharacterListingUIState(
    val isContentLoading: Boolean = true,
    val isPaginationInProgress: Boolean = false,
    val characters: List<CharacterModel> = listOf(),
    val next: String? = null
)