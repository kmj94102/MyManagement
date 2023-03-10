package com.example.mymanagement.database

import androidx.room.*
import com.example.mymanagement.database.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM FavoriteEntity WHERE id = :id")
    suspend fun deleteFavorite(id: String)

    @Query("SELECT EXISTS(SELECT * FROM FavoriteEntity WHERE id = :id)")
    suspend fun fetchFavoriteCountById(id: String): Boolean

    @Transaction
    suspend fun favoriteInsertOrDelete(
        type: String,
        id: String,
        name: String
    ) {
        if (fetchFavoriteCountById(id)){
            deleteFavorite(id)
        } else {
            insertFavorite(
                FavoriteEntity(
                    index = 0,
                    type = type,
                    startTime = "",
                    endTime = "",
                    name = name,
                    id = id,
                    timeStamp = Calendar.getInstance().timeInMillis
                )
            )
        }
    }

    @Query("SELECT * FROM FavoriteEntity ORDER BY startTime ASC, timeStamp ASC")
    fun fetchFavoriteList(): Flow<List<FavoriteEntity>>

    @Query("SELECT EXISTS(SELECT * FROM FavoriteEntity WHERE id = :id)")
    fun fetchFavoriteById(id: String): Flow<Boolean>

}