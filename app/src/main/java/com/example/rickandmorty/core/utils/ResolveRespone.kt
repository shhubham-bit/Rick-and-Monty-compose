package com.example.rickandmorty.core.utils

import com.example.rickandmorty.core.customException.NoInternetException
import okio.IOException
import retrofit2.Response

interface ResponseDelegate{
    suspend fun <T> resolveResponse(api: suspend () -> Response<T>): Result<T?>
}

class NetworkResponseDelegateImpl: ResponseDelegate{

    override suspend fun <T> resolveResponse(api: suspend () -> Response<T>): Result<T?> {
        val result = api.invoke()
        return try {
            if(result.isSuccessful){
                Result.success(result.body())
            }else{
                Result.failure(Exception())
            }
        }catch (e: IOException){
            Result.failure(NoInternetException("Not Connected to Internet"))
        }
    }
}