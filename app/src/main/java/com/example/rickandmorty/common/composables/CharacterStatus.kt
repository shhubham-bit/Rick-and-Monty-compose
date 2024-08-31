package com.example.rickandmorty.common.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.characterListing.domain.model.CharacterStatus

@Composable
fun CharacterStatusComposable(status: CharacterStatus, modifier: Modifier = Modifier){
    val (color, tag) = when(status){
        CharacterStatus.ALIVE -> { Pair(Color.Green, "Alive")}
        CharacterStatus.DEAD -> { Pair(Color.Red, "Dead") }
        CharacterStatus.UNKNOWN -> { Pair(Color.Gray, "Unknown") }
    }
    
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Canvas(modifier = Modifier.size(10.dp)) {
                drawCircle(color = color)
            }
            Text(
                text = tag,
                modifier = Modifier.padding(horizontal = 4.dp),
                style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun PreviewCharacterStatusComposable(){
    Box {
        CharacterStatusComposable(status = CharacterStatus.ALIVE)
    }
}