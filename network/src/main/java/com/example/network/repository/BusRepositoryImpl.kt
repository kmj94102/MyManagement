package com.example.network.repository

import com.example.network.model.BusEstimatedArrivalInfo
import com.example.network.service.BusClient
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class BusRepositoryImpl @Inject constructor(
    private val client: BusClient
) : BusRepository {

    override fun fetchEstimatedArrivalInfoList(
        cityCode: Int,
        nodeId: String,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        val list = mutableListOf<BusEstimatedArrivalInfo>()
        client.fetchEstimatedArrivalInfoList(
            cityCode = cityCode,
            nodeId = nodeId
        ).groupBy { it.routeId }.forEach { (_, value) ->
            list.add(
                BusEstimatedArrivalInfo(
                    busNumber = "${value[0].routeNo}",
                    routeId = value[0].routeId,
                    nodeId = value[0].nodeId,
                    nodeName = value[0].nodeNm,
                    arrInfo = value.map { info ->
                        "${minSecFormat(info.arrTime)} (${
                            kotlin.math.max(
                                info.arrPrevStationCnt,
                                1
                            )
                        } 정류장 전)"
                    },
                    isFavorite = false
                )
            )
        }
        emit(list)
    }.onCompletion { onComplete() }.catch {
        it.printStackTrace()
        onError(it.message)
    }


}

private fun minSecFormat(originSec: Int): String {
    val min = originSec / 60
    val sec = originSec % 60

    return if (min == 0 && sec == 0) {
        "곧 도착"
    } else if (min == 0) {
        "${sec.toString().padStart(2, '0')}초"
    } else {
        "${min}분 ${sec.toString().padStart(2, '0')}초"
    }
}