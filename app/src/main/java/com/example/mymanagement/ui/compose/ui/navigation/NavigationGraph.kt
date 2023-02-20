package com.example.mymanagement.ui.compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mymanagement.ui.compose.ui.home.HomeScreen
import com.example.mymanagement.ui.compose.ui.other.OtherScreen
import com.example.mymanagement.ui.compose.ui.schedule.ScheduleScreen
import com.example.mymanagement.ui.compose.ui.transportation.TransportationScreen
import com.example.mymanagement.ui.compose.ui.transportation.bus.BusStationSearchScreen
import com.example.mymanagement.ui.compose.ui.transportation.subway.SubwaySearchScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    changeListener: (NavItem) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItems.Home.item.routeWithPostFix
    ) {
        /** 홈 화면 **/
        composable(
            route = BottomNavItems.Home.item.routeWithPostFix
        ) {
            HomeScreen()
            changeListener(BottomNavItems.Home.item)
        }
        /** 교통 화면 **/
        composable(
            route = BottomNavItems.Transportation.item.routeWithPostFix
        ) {
            TransportationScreen(
                goToSearchBusStation = {
                    navController.navigate(NavScreen.BusStationSearch.item.routeWithPostFix)
                },
                goToSearchSubway = {
                    navController.navigate(NavScreen.SubwaySearch.item.routeWithPostFix)
                }
            )
            changeListener(BottomNavItems.Transportation.item)
        }
        /** 일정 화면 **/
        composable(
            route = BottomNavItems.Schedule.item.routeWithPostFix
        ) {
            ScheduleScreen()
            changeListener(BottomNavItems.Schedule.item)
        }
        /** 기타 화면 **/
        composable(
            route = BottomNavItems.Other.item.routeWithPostFix
        ) {
            OtherScreen()
            changeListener(BottomNavItems.Other.item)
        }
        /** 버스 검색 화면 **/
        composable(
            route = NavScreen.BusStationSearch.item.routeWithPostFix
        ) {
            BusStationSearchScreen()
            changeListener(NavScreen.BusStationSearch.item)
        }
        /** 지하철 검색 화면 **/
        composable(
            route = NavScreen.SubwaySearch.item.routeWithPostFix
        ) {
            SubwaySearchScreen()
            changeListener(NavScreen.SubwaySearch.item)
        }
    }

}