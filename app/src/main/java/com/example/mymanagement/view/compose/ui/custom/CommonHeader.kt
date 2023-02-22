package com.example.mymanagement.view.compose.ui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mymanagement.R
import com.example.mymanagement.util.nonRippleClickable
import com.example.mymanagement.util.shadow
import com.example.mymanagement.util.textStyle16B
import com.example.mymanagement.view.compose.ui.theme.White

@Composable
fun CommonHeader(
    title: String,
    modifier: Modifier = Modifier,
    tailIcons: (@Composable () -> Unit)? = null,
    onBackClick: () -> Unit
) {
    Card(
        backgroundColor = White,
        shape = RoundedCornerShape(0.dp),
        modifier = modifier.fillMaxWidth()
            .shadow(blurRadius = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_prev),
                contentDescription = "뒤로가기",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(10.dp)
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
                    .padding(end = 10.dp)
            ) {
                tailIcons?.invoke()
            }
        }
    }
}