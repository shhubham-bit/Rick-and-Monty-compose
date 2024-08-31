package com.example.rickandmorty.characterDetail.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R
import com.example.rickandmorty.characterDetail.domain.model.CharacterDetailModel
import com.example.rickandmorty.characterDetail.presentation.capitalizeFirstLetter
import com.example.rickandmorty.common.composables.CharacterStatusComposable
import com.example.rickandmorty.common.composables.LoadRemoteImage


@Composable
fun SinglePanelCharacterDetailScreen(characterDetail: CharacterDetailModel, episodes: @Composable () -> Unit){
    Column(modifier = Modifier.fillMaxSize()) {
        Box {
            LoadRemoteImage(
                url = characterDetail.imageUrl,
                modifier = Modifier
                    .aspectRatio(1f)
                    .background(Color.Yellow)
                    .clip(RoundedCornerShape(10.dp)),
                drawablePlaceHolder = R.drawable.character_placeholder
            )
            characterDetail.status?.let {
                CharacterStatusComposable(
                    status = it,
                    modifier = Modifier
                        .padding(top = 20.dp, end = 20.dp)
                        .align(Alignment.TopEnd)

                )
            }
        }
        Text(
            text = characterDetail.name,
            style = MaterialTheme.typography.headlineLarge.copy(textAlign = TextAlign.Center),
            maxLines = 2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, start = 20.dp, end = 20.dp),
            fontWeight = FontWeight.SemiBold
        )
        if(characterDetail.species.isNotBlank()){
            Text(
                text = "● "+characterDetail.species,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 2.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = "● " + (characterDetail.gender?.name?.capitalizeFirstLetter() ?: "Unknown"),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 20.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        Text(
            text = "Episodes",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight(450),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
        )
        episodes()
    }
}