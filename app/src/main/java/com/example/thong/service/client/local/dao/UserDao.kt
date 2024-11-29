package com.example.thong.service.client.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.thong.models.User
import com.example.thong.models.UserDetail

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserDetail(user: UserDetail)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM userDetails")
    suspend fun getAllUserDetails(): List<UserDetail>

    @Query("SELECT * FROM userDetails WHERE login = :login")
    suspend fun getAllUserDetailByLogin(login: String): List<UserDetail>
}