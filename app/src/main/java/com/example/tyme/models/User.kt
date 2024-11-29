package com.example.tyme.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @Expose @SerializedName("login") val login: String,
    @PrimaryKey @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("avatar_url") val avatarUrl: String,
    @Expose @SerializedName("html_url") val htmlUrl: String,
)