package com.example.thong.utils

import com.example.thong.models.Result
import retrofit2.Response

object Utils {
    inline fun <reified T : Any> toResponseResult(response: Response<T>): Result<T> {
        if (response != null) {
            if (response.isSuccessful) {
                return Result.Success(response.body())
            }
            return Result.Failure(Exception(response.message()))
        }
        return Result.Failure(Exception("Bad request/response"))
    }
}