package com.example.thong.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "userDetails")
data class UserDetail(
    @Expose @SerializedName("login") val login: String?,
    @PrimaryKey @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("avatar_url") val avatarUrl: String?,
    @Expose @SerializedName("html_url") val htmlUrl: String?,
    @Expose @SerializedName("location") val location: String?,
    @Expose @SerializedName("followers") val followers: Int?,
    @Expose @SerializedName("following") val following: Int?,
    @Expose @SerializedName("blog") val blog: String?,
)
