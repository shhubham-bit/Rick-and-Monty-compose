package com.example.rickandmorty.characterDetail.presentation.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.rickandmorty.characterDetail.domain.model.CharacterDetailModel
import com.example.rickandmorty.characterDetail.presentation.viewModel.CharacterDetailUIState
import com.example.rickandmorty.characterDetail.presentation.viewModel.CharacterDetailViewModel
import com.example.rickandmorty.characterListing.domain.model.CharacterStatus
import com.example.rickandmorty.characterListing.domain.model.Gender
import com.example.rickandmorty.common.composables.ScreenLoader

@Composable
fun LaunchCharacterDetail(){
    val vm: CharacterDetailViewModel = hiltViewModel()
    val uiState = vm.uiState.collectAsState()
    CharacterDetailScreen(uiState = uiState.value) {
        vm.getCharacterDetail()
    }
}

@Composable
private fun CharacterDetailScreen(uiState: CharacterDetailUIState, actionLanding: () -> Unit){
    LaunchedEffect(key1 = true) {
        actionLanding()
    }
    uiState.detail?.let {
        val windowSize = currentWindowAdaptiveInfo().windowSizeClass
        if(windowSize.windowWidthSizeClass == WindowWidthSizeClass.COMPACT){
            SinglePanelCharacterDetailScreen(characterDetail = uiState.detail){
                Episodes(
                    appearance = uiState.appearance,
                    Modifier.padding(vertical = 5.dp, horizontal = 20.dp)
                )
            }
        }else{
            TwoPanelCharacterDetailScreen(characterDetail = uiState.detail){
                Episodes(appearance = uiState.appearance)
            }
        }
    } ?: run {  ScreenLoader() }

}

@Composable
@Preview(showSystemUi = true)
fun PreviewCharacterDetailScreen(){
    CharacterDetailScreen(
        uiState = CharacterDetailUIState(
            isLoading = false,
            id = 1,
            detail = CharacterDetailModel(
                id = 1,
                name = "Sample",
                status = CharacterStatus.ALIVE,
                gender = Gender.GENDERLESS,
                episodes = listOf(),
                imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                species = "Human"
            )
        ),
        actionLanding = {}
    )
}