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

    @Query("SELECT COUNT(*) FROM FavoriteEntity WHERE id = :id")
    suspend fun fetchFavoriteById(id: String): Int

    @Transaction
    suspend fun favoriteInsertOrDelete(
        type: String,
        id: String,
        name: String
    ) {
        if (fetchFavoriteById(id) > 0){
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

}