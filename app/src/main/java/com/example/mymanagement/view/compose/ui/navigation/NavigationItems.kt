package com.example.mymanagement.view.compose.ui.navigation

import androidx.annotation.DrawableRes
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
    override val routeWithPostFix: String = route,
    val tailIcons: (@Composable () -> Unit)? = null,
) : NavItem

data class BottomNavItem(
    override val title: String,
    override val route: String,
    override val routeWithPostFix: String = route,
    @DrawableRes
    val icon: Int
) : NavItem

sealed class NavScreen(val item: MainNavItem) {
    object SubwaySearch: NavScreen(
        MainNavItem(
            title = "지하철 검색",
            route = "SearchSubwayScreen"
        )
    )
    object BusStationSearch: NavScreen(
        MainNavItem(
            title = "버스 정류소 검색",
            route = "SearchBusStation"
        )
    )
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