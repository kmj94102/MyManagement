package com.example.mymanagement.database

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.mymanagement.database.entity.FavoriteEntity
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
     * @param lineRegexp 선택한 호선 정보 ex) 전체 -> .* / 1호선, 3호선 포함 -> (^|.*)(1호선|3호선)(.*|$)
     * **/
    @Query("SELECT stationCode, stationName, group_concat(lineNum,  ',') lineNames, CASE WHEN Favorite.id IS NULL THEN 0 ELSE 1 END AS isFavorite\n" +
            "FROM StationEntity as Station\n" +
            "LEFT JOIN FavoriteEntity as Favorite\n" +
            "ON Station.stationCode = Favorite.id\n" +
            "WHERE stationName LIKE '%' || :stationName || '%' AND isFavorite IN (:allOrFavorite)\n" +
            "GROUP BY stationName\n" +
//            "HAVING lineNames REGEXP '(^|.*)(1호선|3호선)(.*|\$)'\n" +
            "ORDER BY isFavorite DESC, stationName ASC")
    fun fetchStationItems(
        stationName: String = "%%",
        allOrFavorite: List<Int>,
//        condition: String = "",
    ): Flow<List<StationItem>>

    @Query("SELECT lineNum FROM StationEntity GROUP BY lineNum")
    suspend fun fetchStationLineNumbers(): List<String>

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
                    id = id
                )
            )
        }
    }

    @Query("SELECT stationName FROM StationEntity WHERE stationCode = :stationId")
    suspend fun fetchSubwayName(stationId: String): String

}