package com.example.rickandmorty.characterDetail.presentation

fun String.capitalizeFirstLetter(): String {
    return this.replaceFirstChar { it.titlecase() }
}