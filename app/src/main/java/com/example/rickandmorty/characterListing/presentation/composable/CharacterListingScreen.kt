package com.example.rickandmorty.characterListing.presentation.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.rickandmorty.R
import com.example.rickandmorty.characterDetail.presentation.capitalizeFirstLetter
import com.example.rickandmorty.characterListing.domain.model.CharacterModel
import com.example.rickandmorty.characterListing.domain.model.CharacterStatus
import com.example.rickandmorty.characterListing.domain.model.Gender
import com.example.rickandmorty.characterListing.presentation.viewModel.CharacterListingUIState
import com.example.rickandmorty.characterListing.presentation.viewModel.CharacterListingViewModel
import com.example.rickandmorty.common.composables.CharacterStatusComposable
import com.example.rickandmorty.common.composables.LoadRemoteImage
import com.example.rickandmorty.common.composables.ScreenLoader

private const val PAGINATION_THRESHOLD = 8
@Composable
fun LaunchCharacterListing(
    vm: CharacterListingViewModel,
    uiState: CharacterListingUIState,
    navigateToDetail: (id: Int) -> Unit) {
    CharacterListingScreen(
        uiState = uiState,
        actionFetch = vm::getCharacterListing,
        actionPagination = vm::getPaginationCharacterListing,
        navigateToDetail = navigateToDetail
    )
}

@Composable
fun CharacterListingScreen(
    uiState: CharacterListingUIState,
    actionFetch: () -> Unit,
    actionPagination: () -> Unit,
    navigateToDetail: (id: Int) -> Unit
){
    val scrollState = rememberLazyGridState()
    LaunchedEffect(key1 = uiState.characters.isEmpty()){
        actionFetch()
    }

    val totalVisibleItem = remember {
        derivedStateOf{scrollState.firstVisibleItemIndex + scrollState.layoutInfo.visibleItemsInfo.size}
    }

    LaunchedEffect(totalVisibleItem.value) {
        if((totalVisibleItem.value + PAGINATION_THRESHOLD > uiState.characters.size) && uiState.characters.isNotEmpty() && !uiState.isPaginationInProgress){
            actionPagination()
        }
    }
    val windowSize = currentWindowAdaptiveInfo().windowSizeClass
    val gridColumn = if(windowSize.windowWidthSizeClass == WindowWidthSizeClass.COMPACT){
        GridCells.Fixed(2)
    }else{
        GridCells.Adaptive(minSize = 200.dp)
    }
    if(uiState.isContentLoading){
       ScreenLoader()
    }else{
        CharactersGrid(uiState.characters, scrollState, navigateToDetail, gridColumn)
    }
}

@Composable
fun CharactersGrid(
    characters: List<CharacterModel>,
    scrollState: LazyGridState,
    navigateToDetail: (id: Int) -> Unit,
    gridColumn: GridCells,
){

    Box(modifier = Modifier.safeDrawingPadding()) {
        LazyVerticalGrid(
            state = scrollState,
            columns = gridColumn,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = 10.dp, end = 10.dp, top = 54.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(count = characters.size, key = {index -> characters[index].id}){ index ->
                GridItem(
                    characterModel = characters[index],
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navigateToDetail(characters[index].id) }
                )
            }
        }
        Header()
    }

}

@Composable
fun Header(){
    Text(
        text = "Characters",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = 20.dp)
    )
}


@Composable
fun GridItem(characterModel: CharacterModel, modifier: Modifier = Modifier){
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp,0.dp,20.dp,0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier.padding(bottom = 5.dp)
        ) {
            Box(modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                ) {
                LoadRemoteImage(
                    url = characterModel.imageUrl,
                    drawablePlaceHolder = R.drawable.character_placeholder,
                    modifier = Modifier.fillMaxSize()
                )
                characterModel.status?.let {
                    CharacterStatusComposable(
                        status = it,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopEnd)

                    )
                }
            }
            Text(
                text = characterModel.name,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Text(
                text = "${characterModel.species} (${characterModel.gender?.name?.capitalizeFirstLetter()})",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 4.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewGridItem(){
    GridItem(characterModel = CharacterModel(
        id = 1,
        name = "Rick Sanchez",
        status = CharacterStatus.ALIVE,
        gender = Gender.MALE,
        species = "Humans",
        imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
    ),
        Modifier
    )
}