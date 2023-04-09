package com.example.mymanagement.view.compose.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mymanagement.R
import com.example.mymanagement.util.textStyle16B
import com.example.mymanagement.view.compose.ui.theme.Green

@Composable
fun LoginScreen(
    goToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val status = viewModel.state.value
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Green)
    ) {
        Button(
            onClick = {
                viewModel.kakaoLogin(context)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFFFEB00)
            ),
            elevation = null,
            shape = RoundedCornerShape(6.dp),
            contentPadding = PaddingValues(vertical = 10.dp, horizontal = 20.dp),
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_kakao_logo),
                contentDescription = null,
                modifier = Modifier.size(24.dp, 22.dp)
            )
            Text(
                text = "카카오 로그인",
                style = textStyle16B().copy(fontSize = 18.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
    }

    LaunchedEffect(status) {
        if (status is LoginViewModel.Status.Success) {
            goToHome()
        }
    }
}