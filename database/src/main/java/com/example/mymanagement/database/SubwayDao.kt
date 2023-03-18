package com.example.mymanagement.database

import androidx.room.*
import com.example.mymanagement.database.entity.StationEntity
import com.example.mymanagement.database.entity.StationItem
import kotlinx.coroutines.flow.Flow

@Dao
interface SubwayDao {

    /** 지하철 역 정보 추가 **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubwayStation(list: List<StationEntity>)

    /** 등록된 지하철 역 개수 **/
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
        stationName: String = "",
        allOrFavorite: List<Int>,
    ): Flow<List<StationItem>>

    /** 지하철 호선 종류 조회 **/
    @Query("SELECT lineNum FROM StationEntity GROUP BY lineNum")
    suspend fun fetchStationLineNumbers(): List<String>

    /** stationId로 지하철 역 이름 조회 **/
    @Query("SELECT stationName FROM StationEntity WHERE stationId = :stationId")
    suspend fun fetchSubwayNameById(stationId: String): String

    /** stationId로 stationCode 조회 **/
    @Query("SELECT stationCode FROM StationEntity WHERE stationId = :stationId")
    suspend fun fetchSubwayCodeById(stationId: String): String

}