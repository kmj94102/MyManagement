package com.example.mymanagement.ui.compose.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mymanagement.R
import com.example.mymanagement.ui.compose.ui.theme.White
import com.example.mymanagement.util.nonRippleClickable
import com.example.mymanagement.util.textStyle16B

@Composable
fun NavigationTopBar(
    title: String,
    tailIcons: (@Composable () -> Unit)? = null,
    onBackClick: () -> Unit
) {
    TopAppBar(
        backgroundColor = White,
        elevation = 6.dp,
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.ic_prev),
                contentDescription = "뒤로가기",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(vertical = 16.dp, horizontal = 24.dp)
                    .size(24.dp)
                    .nonRippleClickable {
                        onBackClick()
                    }
            )

            Text(
                text = title,
                style = textStyle16B(),
                modifier = Modifier.align(Alignment.Center)
            )

            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 24.dp)
            ) {
                tailIcons?.invoke()
            }
        }
    }
}