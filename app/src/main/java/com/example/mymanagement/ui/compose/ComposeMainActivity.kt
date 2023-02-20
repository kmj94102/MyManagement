package com.example.mymanagement.ui.compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.mymanagement.ui.compose.ui.navigation.*
import com.example.mymanagement.ui.compose.ui.theme.Black
import com.example.mymanagement.ui.compose.ui.theme.MyManagementTheme
import com.example.mymanagement.ui.compose.ui.theme.White
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyManagementTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = White
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var selectItem by remember {
        mutableStateOf<NavItem>(BottomNavItems.Home.item)
    }
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(Black)

    Scaffold(
        backgroundColor = White,
        topBar = {
            if (selectItem is MainNavItem) {
                NavigationTopBar(
                    title = selectItem.title,
                    tailIcons = (selectItem as? NavScreen)?.item?.tailIcons,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                onClick = {
                    navController.navigate(it.routeWithPostFix)
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavigationGraph(
                navController = navController,
                changeListener = { item ->
                    selectItem = item
                }
            )
        }
    }
}