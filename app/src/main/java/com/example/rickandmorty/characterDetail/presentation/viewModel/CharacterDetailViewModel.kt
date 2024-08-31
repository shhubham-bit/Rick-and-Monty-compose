package com.example.rickandmorty.characterDetail.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.characterDetail.domain.model.CharacterAppearanceModel
import com.example.rickandmorty.characterDetail.domain.model.CharacterDetailModel
import com.example.rickandmorty.characterDetail.domain.repository.CharacterDetailRepository
import com.example.rickandmorty.common.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val characterDetailRepository: CharacterDetailRepository
): ViewModel() {

    private val characterId: Int =  savedStateHandle.get<Int>("character_id")!!

    private val _uiState: MutableStateFlow<CharacterDetailUIState> = MutableStateFlow(CharacterDetailUIState(id = 0))
    val uiState: StateFlow<CharacterDetailUIState> = _uiState.asStateFlow()

    fun getCharacterDetail(){
        launchInViewModelScope {
            val result = characterDetailRepository.getCharacterDetail(id = characterId.toString())
            when(result){
                is ResponseState.Success -> {
                    launchInViewModelScope {
                        val data = result.data
                        _uiState.emit(
                            uiState.value.copy(isLoading = false, detail = data)
                        )
                        getEpisodeDetail()
                    }
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

    private fun getEpisodeDetail(){
        viewModelScope.launch {
            val deferredResults: MutableList<Deferred<ResponseState<CharacterAppearanceModel?>>> = mutableListOf()
            uiState.value.detail?.episodes?.forEach { url ->
               deferredResults.add(async { characterDetailRepository.getCharacterEpisodeDetail(url) })
            }
            val results = awaitAll(*deferredResults.toTypedArray())
            val episodes: MutableList<CharacterAppearanceModel> = mutableListOf()
            results.forEach { result ->
                when(result){
                    is ResponseState.Success -> {
                        result.data?.let { episodes.add(it) }
                    }
                    else -> {

                    }
                }
            }
            if(episodes.isNotEmpty()){
                _uiState.emit(uiState.value.copy(appearance = episodes))
            }
        }
    }


}

data class CharacterDetailUIState(
    val isLoading: Boolean = false,
    val id: Int,
    val detail: CharacterDetailModel? = null,
    val appearance: List<CharacterAppearanceModel> = listOf()
)