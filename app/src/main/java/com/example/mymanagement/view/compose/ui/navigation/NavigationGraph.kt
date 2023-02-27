package com.example.mymanagement.view.compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mymanagement.view.compose.ui.home.HomeScreen
import com.example.mymanagement.view.compose.ui.other.OtherScreen
import com.example.mymanagement.view.compose.ui.schedule.ScheduleScreen
import com.example.mymanagement.view.compose.ui.transportation.TransportationScreen
import com.example.mymanagement.view.compose.ui.transportation.bus.BusStationSearchScreen
import com.example.mymanagement.view.compose.ui.transportation.bus.arrival_info.BusStopArrivalInfoScreen
import com.example.mymanagement.view.compose.ui.transportation.subway.SubwaySearchScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    changeListener: (NavItem) -> Unit,
) {
    val onBackClick: () -> Unit = { navController.popBackStack() }

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
            BusStationSearchScreen(
                goToArrivalInfo = { cityCode, nodeId, name ->
                    navController.navigate(
                        makeRouteWithArgs(
                            route = NavScreen.BusStopArrivalInfo.item.route,
                            "$cityCode",
                            nodeId,
                            name
                        )
                    )
                },
                onBackClick = onBackClick
            )
            changeListener(NavScreen.BusStationSearch.item)
        }
        /** 버스 정류소 도착 정보 화면 **/
        composable(
            route = NavScreen.BusStopArrivalInfo.item.routeWithPostFix,
            arguments = listOf(
                navArgument(NavScreen.BusStopArrivalInfo.CityCode) { type = NavType.IntType },
                navArgument(NavScreen.BusStopArrivalInfo.NodeId) { type = NavType.StringType },
                navArgument(NavScreen.BusStopArrivalInfo.Name) { type = NavType.StringType },
            )
        ) { entry ->
            entry.arguments?.getInt(NavScreen.BusStopArrivalInfo.CityCode) ?: return@composable
            entry.arguments?.getString(NavScreen.BusStopArrivalInfo.NodeId) ?: return@composable
            val name = entry.arguments?.getString(
                NavScreen.BusStopArrivalInfo.Name
            ) ?: return@composable

            BusStopArrivalInfoScreen(
                name = name,
                onBackClick = onBackClick
            )

        }
        /** 지하철 검색 화면 **/
        composable(
            route = NavScreen.SubwaySearch.item.routeWithPostFix
        ) {
            SubwaySearchScreen(
                onBackClick = onBackClick
            )
            changeListener(NavScreen.SubwaySearch.item)
        }
    }

}