package com.example.thong.service.repositories

import com.example.thong.models.Result
import com.example.thong.models.User
import com.example.thong.models.UserDetail
import com.example.thong.service.client.ApiService
import com.example.thong.service.client.local.dao.AppDatabase

interface UserRepository {
    suspend fun getUsers(
        since: Int, perPage: Int
    ): Result<MutableList<User>>

    suspend fun getUserDetailOrCache(
        loginUsername: String
    ): Result<UserDetail>

    suspend fun getUsersOrCache(
        since: Int, perPage: Int
    ): Result<MutableList<User>>
}

class UserRepositoryImpl(
    private val apiService: ApiService, private val appDatabase: AppDatabase
) : UserRepository {
    override suspend fun getUsers(
        since: Int, perPage: Int
    ): Result<MutableList<User>> {
        val response = apiService.getUsers(
            since, perPage
        )
        return if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()
            saveData(responseBody!!)
            Result.Success(responseBody)
        } else {
            Result.Failure(Exception(response.message()))
        }
    }

    override suspend fun getUserDetailOrCache(loginUsername: String): Result<UserDetail> {
        val users = appDatabase.userDao().getAllUserDetailByLogin(loginUsername)
        if (users.isEmpty()) {
            val response = apiService.getUserDetail(loginUsername)
            return if (response.isSuccessful && response.body() != null) {
                val responseBody = response.body()
                saveUserDetail(responseBody!!)
                Result.Success(responseBody)
            } else {
                Result.Failure(Exception(response.message()))
            }
        } else {
            return Result.Success(users.first())
        }
    }

    override suspend fun getUsersOrCache(since: Int, perPage: Int): Result<MutableList<User>> {
        val users = appDatabase.userDao().getAllUsers()
        if (users.isEmpty()) {
            val response = apiService.getUsers(
                since, perPage
            )
            return if (response.isSuccessful && response.body() != null) {
                val responseBody = response.body()
                saveData(responseBody!!)
                Result.Success(responseBody)
            } else {
                Result.Failure(Exception(response.message()))
            }
        } else {
            return Result.Success(users.toMutableList())
        }
    }

    private suspend fun saveData(users: MutableList<User>) {
        val userDB = appDatabase.userDao().getAllUsers()
        for (user in users) {
            if (!userDB.contains(user))
                appDatabase.userDao().insertUser(user)
        }
    }

    private suspend fun saveUserDetail(userDetail: UserDetail) {
        val userDB = appDatabase.userDao().getAllUserDetails()
        if (!userDB.contains(userDetail))
            appDatabase.userDao().insertUserDetail(userDetail)
    }

}