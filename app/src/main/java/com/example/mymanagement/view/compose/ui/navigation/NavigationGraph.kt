package com.example.mymanagement.view.compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mymanagement.view.compose.ui.home.HomeScreen
import com.example.mymanagement.view.compose.ui.login.LoginScreen
import com.example.mymanagement.view.compose.ui.other.OtherScreen
import com.example.mymanagement.view.compose.ui.schedule.ScheduleScreen
import com.example.mymanagement.view.compose.ui.schedule.register.RegisterScheduleScreen
import com.example.mymanagement.view.compose.ui.transportation.TransportationScreen
import com.example.mymanagement.view.compose.ui.transportation.bus.BusStationSearchScreen
import com.example.mymanagement.view.compose.ui.transportation.bus.arrival_info.BusStopArrivalInfoScreen
import com.example.mymanagement.view.compose.ui.transportation.bus.route.BusStopRouteScreen
import com.example.mymanagement.view.compose.ui.transportation.subway.SubwaySearchScreen
import com.example.mymanagement.view.compose.ui.transportation.subway.destination_route.DestinationRouteScreen
import com.example.mymanagement.view.compose.ui.transportation.subway.schedule.SubwayScheduleScreen

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    val onBackClick: () -> Unit = { navController.popBackStack() }

    NavHost(
        navController = navController,
        startDestination = BottomNavItems.Home.item.routeWithPostFix
    ) {
        /** 로그인 **/
        composable(
            route = NavScreen.Login.item.routeWithPostFix
        ) {
            LoginScreen(
                goToHome = {
                    navController.navigate(BottomNavItems.Home.item.routeWithPostFix) {
                        popUpTo(0)
                    }
                }
            )
        } // 로그인
        /** 홈 화면 **/
        composable(
            route = BottomNavItems.Home.item.routeWithPostFix
        ) {
            HomeScreen(
                goToLogin = {
                    navController.navigate(NavScreen.Login.item.routeWithPostFix) {
                        popUpTo(0)
                    }
                }
            )
        } // 홈 화면
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
        } // 교통 화면
        /** 일정 화면 **/
        composable(
            route = BottomNavItems.Schedule.item.routeWithPostFix
        ) {
            ScheduleScreen(
                goToRegister = { date ->
                    navController.navigate(
                        makeRouteWithArgs(
                            NavScreen.RegisterSchedule.item.route,
                            date
                        )
                    )
                }
            )
        } // 일정 화면
        /** 일정 등록 화면 **/
        composable(
            route = NavScreen.RegisterSchedule.item.routeWithPostFix,
            arguments = listOf(
                navArgument(NavScreen.RegisterSchedule.Date) { type = NavType.StringType }
            )
        ) { entry ->
            val date =
                entry.arguments?.getString(NavScreen.RegisterSchedule.Date) ?: return@composable
            RegisterScheduleScreen(
                date = date,
                onBackClick = onBackClick
            )
        } // 일정 등록 화면
        /** 기타 화면 **/
        composable(
            route = BottomNavItems.Other.item.routeWithPostFix
        ) {
            OtherScreen()
        } // 기타 화면
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
        } //버스 검색 화면
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
                onBackClick = onBackClick,
                onRouteClick = { cityCode, routeId, number, nodeId ->
                    navController.navigate(
                        makeRouteWithArgs(
                            route = NavScreen.BusStopRoute.item.route,
                            "$cityCode",
                            routeId,
                            number,
                            nodeId
                        )
                    )
                }
            )
        } // 버스 정류소 도착 정보 화면
        /** 버스 노선 화면 **/
        composable(
            route = NavScreen.BusStopRoute.item.routeWithPostFix,
            arguments = listOf(
                navArgument(NavScreen.BusStopRoute.CityCode) { type = NavType.IntType },
                navArgument(NavScreen.BusStopRoute.RouteId) { type = NavType.StringType },
                navArgument(NavScreen.BusStopRoute.Number) { type = NavType.StringType },
                navArgument(NavScreen.BusStopRoute.NodeId) { type = NavType.StringType },
            )
        ) { entry ->
            entry.arguments?.getInt(NavScreen.BusStopRoute.CityCode) ?: return@composable
            entry.arguments?.getString(NavScreen.BusStopRoute.RouteId) ?: return@composable
            val number =
                entry.arguments?.getString(NavScreen.BusStopRoute.Number) ?: return@composable
            val nodeId =
                entry.arguments?.getString(NavScreen.BusStopRoute.NodeId) ?: return@composable

            BusStopRouteScreen(
                number = number,
                nodeId = nodeId,
                onBackClick = onBackClick
            )
        } // 버스 노선 화면
        /** 지하철 검색 화면 **/
        composable(
            route = NavScreen.SubwaySearch.item.routeWithPostFix
        ) {
            SubwaySearchScreen(
                onBackClick = onBackClick,
                goToDestinationRoute = { start, end ->
                    navController.navigate(
                        makeRouteWithArgs(
                            NavScreen.SubwayDestinationRoute.item.route,
                            start.stationCode,
                            start.stationName,
                            end.stationCode,
                            end.stationName
                        )
                    )
                },
                goToSchedule = { current, prev, next, code ->
                    navController.navigate(
                        makeRouteWithArgs(
                            NavScreen.SubwaySchedule.item.route,
                            current,
                            prev,
                            next,
                            code
                        )
                    )
                }
            )
        } // 지하철 검색 화면
        /** 지하철 목적지 경로 화면 **/
        composable(
            route = NavScreen.SubwayDestinationRoute.item.routeWithPostFix,
            arguments = listOf(
                navArgument(NavScreen.SubwayDestinationRoute.StartStationCode) {
                    type = NavType.StringType
                },
                navArgument(NavScreen.SubwayDestinationRoute.StartStationName) {
                    type = NavType.StringType
                },
                navArgument(NavScreen.SubwayDestinationRoute.EndStationCode) {
                    type = NavType.StringType
                },
                navArgument(NavScreen.SubwayDestinationRoute.EndStationName) {
                    type = NavType.StringType
                },
            )
        ) { entry ->
            entry.arguments?.getString(NavScreen.SubwayDestinationRoute.StartStationCode)
                ?: return@composable
            entry.arguments?.getString(NavScreen.SubwayDestinationRoute.EndStationCode)
                ?: return@composable

            DestinationRouteScreen(
                startStation = entry.arguments?.getString(NavScreen.SubwayDestinationRoute.StartStationName)
                    ?: "",
                endStation = entry.arguments?.getString(NavScreen.SubwayDestinationRoute.EndStationName)
                    ?: "",
                onBackClick = onBackClick
            )

        } // 지하철 목적지 경로 화면
        /** 지하철 시간표 화면 **/
        composable(
            route = NavScreen.SubwaySchedule.item.routeWithPostFix,
            arguments = listOf(
                navArgument(NavScreen.SubwaySchedule.CurrentStationName) {
                    type = NavType.StringType
                },
                navArgument(NavScreen.SubwaySchedule.PrevStationName) { type = NavType.StringType },
                navArgument(NavScreen.SubwaySchedule.NextStationName) { type = NavType.StringType },
                navArgument(NavScreen.SubwaySchedule.StationCode) { type = NavType.StringType },
            )
        ) { entry ->
            entry.arguments?.getString(NavScreen.SubwaySchedule.StationCode) ?: return@composable
            val current = entry.arguments?.getString(NavScreen.SubwaySchedule.CurrentStationName)
                ?: return@composable
            val prev = entry.arguments?.getString(NavScreen.SubwaySchedule.PrevStationName) ?: ""
            val next = entry.arguments?.getString(NavScreen.SubwaySchedule.NextStationName) ?: ""

            SubwayScheduleScreen(
                current = current,
                prev = prev,
                next = next,
                onBackClick = onBackClick
            )
        } // 지하철 시간표 화면
    }

}