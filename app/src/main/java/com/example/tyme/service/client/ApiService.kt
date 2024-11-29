package com.example.tyme.service.client

import com.example.tyme.models.User
import com.example.tyme.models.UserDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int, @Query("per_page") perPage: Int
    ): Response<MutableList<User>>

    @GET("users/{login_username}")
    suspend fun getUserDetail(
        @Path("login_username") loginUsername: String,
    ): Response<UserDetail>
}