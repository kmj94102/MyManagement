package com.example.mymanagement.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TokenEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiresAt: Long
)