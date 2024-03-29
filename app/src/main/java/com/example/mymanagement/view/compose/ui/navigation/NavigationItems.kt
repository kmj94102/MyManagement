package com.example.mymanagement.view.compose.ui.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.example.mymanagement.R

interface NavItem {
    val title: String
    val route: String
    val routeWithPostFix: String
}

data class MainNavItem(
    override val title: String,
    override val route: String,
    override val routeWithPostFix: String = route
) : NavItem

data class BottomNavItem(
    override val title: String,
    override val route: String,
    override val routeWithPostFix: String = route,
    @DrawableRes
    val icon: Int
) : NavItem

fun makeRouteWithArgs(route: String, vararg args: String): String = buildString {
    append(route)
    args.forEach {
        append("/$it")
    }
}

sealed class NavScreen(val item: MainNavItem) {
    object Login: NavScreen(
        MainNavItem(
            title = "로그인",
            route = "Login"
        )
    )
    object SubwaySearch: NavScreen(
        MainNavItem(
            title = "지하철 검색",
            route = "SearchSubwayScreen"
        )
    )

    object SubwayDestinationRoute: NavScreen(
        MainNavItem(
            title = "지하철 목적지 경로",
            route = "DestinationRoute",
            routeWithPostFix = "DestinationRoute/{startCode}/{startName}/{endCode}/{endName}"
        )
    ) {
        const val StartStationCode = "startCode"
        const val StartStationName = "startName"
        const val EndStationCode = "endCode"
        const val EndStationName = "endName"
    }

    object SubwaySchedule: NavScreen(
        MainNavItem(
            title = "",
            route = "SubwaySchedule",
            routeWithPostFix = "SubwaySchedule/{stationName}/{prevName}/{nextName}/{stationCode}"
        )
    ) {
        const val CurrentStationName = "stationName"
        const val PrevStationName = "prevName"
        const val NextStationName = "nextName"
        const val StationCode = "stationCode"
    }

    object BusStationSearch: NavScreen(
        MainNavItem(
            title = "버스 정류소 검색",
            route = "SearchBusStation"
        )
    )

    object BusStopArrivalInfo: NavScreen(
        MainNavItem(
            title = "",
            route = "BusStopArrivalInfo",
            routeWithPostFix = "BusStopArrivalInfo/{cityCode}/{nodeId}/{name}",
        )
    ) {
        const val CityCode = "cityCode"
        const val NodeId = "nodeId"
        const val Name = "name"
    }

    object BusStopRoute: NavScreen(
        MainNavItem(
            title = "",
            route = "BusStopRoute",
            routeWithPostFix = "BusStopRoute/{cityCode}/{routeId}/{number}/{nodeId}"
        )
    ) {
        const val CityCode = "cityCode"
        const val RouteId = "routeId"
        const val Number = "number"
        const val NodeId = "nodeId"
    }

    object RegisterSchedule: NavScreen(
        MainNavItem(
            title = "일정 등록",
            route = "RegisterSchedule",
            routeWithPostFix = "RegisterSchedule/{date}"
        )
    ) {
        const val Date = "date"
    }
}

enum class BottomNavItems(val item: BottomNavItem) {
    Home(
        item = BottomNavItem(
            title = "홈",
            route = "Home",
            icon = R.drawable.ic_home
        )
    ),
    Transportation(
        item = BottomNavItem(
            title = "교통",
            route = "Transportation",
            icon = R.drawable.ic_transportation
        )
    ),
    Schedule(
        item = BottomNavItem(
            title = "일정",
            route = "Schedule",
            icon = R.drawable.ic_calendar
        )
    ),
    Other(
        item = BottomNavItem(
            title = "기타",
            route = "Other",
            icon = R.drawable.ic_other
        )
    )
}