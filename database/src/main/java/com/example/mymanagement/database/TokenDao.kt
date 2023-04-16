package com.example.mymanagement.database

import androidx.room.*
import com.example.mymanagement.database.entity.TokenEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TokenDao {
    /**
     * Token 등록
     * **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToken(token: TokenEntity)

    /**
     * AccessToken 조회
     * **/
    @Query("SELECT accessToken FROM TokenEntity LIMIT 1")
    suspend fun fetchAccessToken(): String

    /**
     * AccessToken(Flow)
     * **/
    @Query("SELECT accessToken FROM TokenEntity LIMIT 1")
    fun fetchAccessTokenFlow(): Flow<String>

    /**
     * Token 조회
     * **/
    @Query("SELECT * FROM TokenEntity LIMIT 1")
    suspend fun fetchToken(): TokenEntity

    /**
     * 토큰 업데이트
     * **/
    @Update
    suspend fun updateToken(token: TokenEntity)

    /**
     * 토큰 제거
     * **/
    @Query("DELETE FROM TokenEntity")
    suspend fun deleteAll()

}