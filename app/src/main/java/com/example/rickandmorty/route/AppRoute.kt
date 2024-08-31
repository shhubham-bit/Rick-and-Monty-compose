package com.example.rickandmorty.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rickandmorty.characterDetail.presentation.composables.LaunchCharacterDetail
import com.example.rickandmorty.characterListing.presentation.composable.LaunchCharacterListing
import com.example.rickandmorty.characterListing.presentation.viewModel.CharacterListingViewModel

@Composable
fun AppRoute(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppRouteScreen.CHARACTER_LISTING.name){
        composable(route = AppRouteScreen.CHARACTER_LISTING.name){
            val vm: CharacterListingViewModel = hiltViewModel()
            val uiState = vm.uiState.collectAsState()
            LaunchCharacterListing(
                vm = vm,
                uiState = uiState.value
            ) { characterId ->
                navController.navigate("${AppRouteScreen.CHARACTER_DETAIL.name}/${characterId}")
            }
        }
        composable(
            route = "${AppRouteScreen.CHARACTER_DETAIL.name}/{character_id}",
            arguments = listOf(navArgument("character_id") { type = NavType.IntType})
        ){
            LaunchCharacterDetail()
        }
    }
}


private enum class AppRouteScreen{
    CHARACTER_LISTING,
    CHARACTER_DETAIL
}