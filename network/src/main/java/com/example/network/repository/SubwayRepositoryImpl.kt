package com.example.network.repository

import android.content.Context
import com.example.mymanagement.database.SubwayDao
import com.example.mymanagement.database.entity.FavoriteEntity
import com.example.mymanagement.database.entity.StationEntity
import com.example.mymanagement.database.entity.StationItem
import com.example.network.R
import com.example.network.model.SubwayArrivalInfo
import com.example.network.service.SubwayClient
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class SubwayRepositoryImpl @Inject constructor(
    private val subwayDao: SubwayDao,
    private val client: SubwayClient,
    @ApplicationContext
    private val context: Context
) : SubwayRepository {

    override suspend fun fetchStationItems(
        stationName: String,
        allOrFavorite: List<Int>,
    ): Flow<List<StationItem>> {
        if (subwayDao.getNumberOfStations() <= 0) {
            val inputStream = context.resources.openRawResource(R.raw.station_info)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val stationInfoList = mutableListOf<StationEntity>()
            reader.forEachLine {
                val infoList = it.split(',')
                stationInfoList.add(
                    StationEntity(
                        lineNum = infoList[0],
                        stationCode = infoList[1],
                        stationName = infoList[2]
                    )
                )
            }

            subwayDao.insertSubwayStation(stationInfoList.drop(1))
        }

        return subwayDao.fetchStationItems(
            stationName = stationName,
            allOrFavorite = allOrFavorite
        )
    }

    override suspend fun fetchStationLineNumbers(): Map<String, Boolean> = try {
        subwayDao.fetchStationLineNumbers().associateWith { true }
    } catch (e: Exception) {
        e.printStackTrace()
        mapOf()
    }

    override suspend fun updateFavorite(
        item: StationItem
    ) = try {
        subwayDao.favoriteInsertOrDelete(
            type = FavoriteEntity.TypeSubway,
            id = item.stationCode,
            name = item.stationName
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }

    override suspend fun fetchRealtimeStationArrivals(
        keyword: String
    ) = flow {
        val result = client
            .fetchRealtimeStationArrivals(keyword)
            .sortedWith(
                compareBy<SubwayArrivalInfo> { it.updnLine }.thenBy { it.arrTime }
            )
            .groupBy { it.subwayLineId }
        emit(result)
    }

}