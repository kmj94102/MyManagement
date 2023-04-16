package com.example.mymanagement.view.compose.ui.custom

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.mymanagement.util.nonRippleClickable
import com.example.mymanagement.util.textStyle16
import com.example.mymanagement.view.compose.ui.theme.Black
import com.example.mymanagement.view.compose.ui.theme.Gray
import com.example.mymanagement.view.compose.ui.theme.Green
import com.example.mymanagement.view.compose.ui.theme.White

@Composable
fun CommonRadio(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = textStyle16(),
    isEnable: Boolean = true,
    check: Boolean,
    onCheckedChange: (String) -> Unit,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.nonRippleClickable {
            if (isEnable) {
                onCheckedChange(text)
            }
        }
    ) {
        val borderColor = animateColorAsState(
            targetValue = if (check) White else Gray,
            animationSpec = tween(durationMillis = 250)
        )
        val fillColor = animateColorAsState(
            targetValue = if (check) Green else White,
            animationSpec = tween(durationMillis = 500)
        )

        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .border(1.dp, borderColor.value, CircleShape)
                .background(fillColor.value)
        )
        Spacer(modifier = Modifier.padding(start = 8.dp))
        Text(text = text, style = textStyle.copy(color = if (isEnable) Black else Gray))

    }
}