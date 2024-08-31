package com.example.rickandmorty.characterListing.domain.model

enum class CharacterStatus {
    ALIVE,
    DEAD,
    UNKNOWN;

    companion object {

        // Method to convert a String to an enum
        fun fromString(value: String): CharacterStatus? {
            return entries.find { it.name.equals(value, ignoreCase = true) }
        }
    }
}