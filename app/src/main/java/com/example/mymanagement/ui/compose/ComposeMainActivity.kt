package com.example.mymanagement.ui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.mymanagement.ui.compose.ui.navigation.BottomNavigationBar
import com.example.mymanagement.ui.compose.ui.navigation.NavigationGraph
import com.example.mymanagement.ui.compose.ui.theme.MyManagementTheme
import com.example.mymanagement.ui.compose.ui.theme.White

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
    Scaffold(
        backgroundColor = White,
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
            NavigationGraph(navController = navController)
        }
    }
}