package com.example.mymanagement.database

import androidx.room.*
import com.example.mymanagement.database.entity.StationEntity
import com.example.mymanagement.database.entity.StationItem
import kotlinx.coroutines.flow.Flow

@Dao
interface SubwayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubwayStation(list: List<StationEntity>)

    @Query("SELECT COUNT(*) FROM StationEntity")
    suspend fun getNumberOfStations(): Int

    /**
     * @param stationName 지하철 역 이름
     * @param allOrFavorite 전체의 경우 list(0,1), 즐겨찾기만인 경우 list(1)
     * **/
    @Query("SELECT stationCode, stationId, stationName, group_concat(lineNum,  ',') lineNames, CASE WHEN Favorite.id IS NULL THEN 0 ELSE 1 END AS isFavorite\n" +
            "FROM StationEntity as Station\n" +
            "LEFT JOIN FavoriteEntity as Favorite\n" +
            "ON Station.stationCode = Favorite.id\n" +
            "WHERE stationName LIKE '%' || :stationName || '%' AND isFavorite IN (:allOrFavorite)\n" +
            "GROUP BY stationName\n" +
            "ORDER BY isFavorite DESC, stationName ASC")
    fun fetchStationItems(
        stationName: String = "%%",
        allOrFavorite: List<Int>,
    ): Flow<List<StationItem>>

    @Query("SELECT lineNum FROM StationEntity GROUP BY lineNum")
    suspend fun fetchStationLineNumbers(): List<String>

    @Query("SELECT stationName FROM StationEntity WHERE stationId = :stationId")
    suspend fun fetchSubwayName(stationId: String): String

    @Query("SELECT stationCode FROM StationEntity WHERE stationId = :stationId")
    suspend fun fetchSubwayCode(stationId: String): String

}