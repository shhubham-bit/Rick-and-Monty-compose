package com.example.rickandmorty.characterDetail.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R
import com.example.rickandmorty.characterDetail.domain.model.CharacterAppearanceModel
import com.example.rickandmorty.common.composables.ScreenLoader


@Composable
fun Episodes(appearance: List<CharacterAppearanceModel>, modifier: Modifier = Modifier.padding(vertical = 5.dp)){
    if(appearance.isEmpty()){
        ScreenLoader()
        return
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
    ) {
        items(count = appearance.size, key = {appearance[it].episode}){
            val episode = appearance[it]
            Box {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.r_m),
                        contentDescription = null,
                        modifier = Modifier
                            .width(70.dp)
                            .height(IntrinsicSize.Max)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Text(text = episode.name)
                        Text(text = episode.airDate)
                        Text(text = episode.episode)
                    }
                }
            }
        }
    }
}