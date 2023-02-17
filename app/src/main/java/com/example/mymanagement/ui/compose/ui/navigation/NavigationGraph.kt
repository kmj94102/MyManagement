package com.example.mymanagement.ui.compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mymanagement.ui.compose.ui.home.HomeScreen
import com.example.mymanagement.ui.compose.ui.other.OtherScreen
import com.example.mymanagement.ui.compose.ui.schedule.ScheduleScreen
import com.example.mymanagement.ui.compose.ui.transportation.TransportationScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItems.Home.item.routeWithPostFix
    ) {
        /** 홈 화면 **/
        composable(
            route = BottomNavItems.Home.item.routeWithPostFix
        ) {
            HomeScreen()
        }
        /** 교통 화면 **/
        composable(
            route = BottomNavItems.Transportation.item.routeWithPostFix
        ) {
            TransportationScreen()
        }
        /** 일정 화면 **/
        composable(
            route = BottomNavItems.Schedule.item.routeWithPostFix
        ) {
            ScheduleScreen()
        }
        /** 기타 화면 **/
        composable(
            route = BottomNavItems.Other.item.routeWithPostFix
        ) {
            OtherScreen()
        }
    }

}