package com.example.rickandmorty.common

sealed class ResponseState<T>(val data: T? = null, val message: String? = null){
    class Success<T>(data: T? = null): ResponseState<T>(data, message = null)
    class Error<T>(message: String): ResponseState<T>()
    class NoInternet<T>(): ResponseState<T>()
}