package com.example.mymanagement.view.compose.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    goToLogin: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val loginState = viewModel.token.value

    Box(modifier = Modifier.fillMaxSize()) {
        Text("home")
        Button(
            onClick = { viewModel.logout() },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(text = "로그아웃")
        }
    }

    LaunchedEffect(loginState) {
        Log.e("+++++", "$loginState")
        if (loginState.isEmpty()) {
            goToLogin()
        }
    }
}