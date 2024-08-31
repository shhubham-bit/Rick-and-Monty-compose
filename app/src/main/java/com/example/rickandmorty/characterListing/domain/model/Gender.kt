package com.example.rickandmorty.characterListing.domain.model

enum class Gender {
    MALE,
    FEMALE,
    GENDERLESS,
    UNKNOWN;

    companion object {

        // Method to convert a String to an enum
        fun fromString(value: String): Gender? {
            return Gender.entries.find { it.name.equals(value, ignoreCase = true) }
        }
    }
}