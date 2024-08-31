package com.example.rickandmorty.core.remote.dto

data class PaginationMetaData(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)