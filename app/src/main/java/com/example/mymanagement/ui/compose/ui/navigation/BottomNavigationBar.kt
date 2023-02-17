package com.example.mymanagement.ui.compose.ui.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mymanagement.ui.compose.ui.theme.Gray
import com.example.mymanagement.ui.compose.ui.theme.Green
import com.example.mymanagement.ui.compose.ui.theme.White

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    onClick: (BottomNavItem) -> Unit
) {
    val items = BottomNavItems.values().map { it.item }

    BottomNavigation(
        backgroundColor = White,
        elevation = 6.dp
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        items.forEach { item ->
            BottomNavigationItem(
                selected = item.routeWithPostFix == backStackEntry?.destination?.route,
                onClick = { onClick(item) },
                icon = {
                    Icon(painter = painterResource(id = item.icon), contentDescription = item.title)
                },
                label = {
                    Text(text = item.title)
                },
                selectedContentColor = Green,
                unselectedContentColor = Gray,
            )
        }
    }
}