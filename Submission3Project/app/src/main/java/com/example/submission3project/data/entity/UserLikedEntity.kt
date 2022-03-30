package com.example.submission3project.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user_like")
@Parcelize
class UserLikedEntity(
    @field:ColumnInfo(name ="id")
    @field:PrimaryKey
    val id:Int = 0,

    @field:ColumnInfo(name="login")
    val login: String,

    @field:ColumnInfo(name="url")
    val url: String,

    @field:ColumnInfo(name="avatar_url")
    val avatarUrl: String,

    @field:ColumnInfo(name="isLiked")
    var isLiked: Boolean
): Parcelable