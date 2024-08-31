package com.example.rickandmorty.common.composables

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.imageLoader

@Composable
fun LoadRemoteImage(
    url: String?,
    @DrawableRes drawablePlaceHolder: Int? = null,
    modifier: Modifier = Modifier
){
    val imageLoader = LocalContext.current.imageLoader
    AsyncImage(
        model = url,
        contentDescription = null,
        imageLoader = imageLoader,
        placeholder = drawablePlaceHolder?.let { painterResource(id = it) },
        modifier = modifier,
        contentScale = ContentScale.FillBounds
    )
}