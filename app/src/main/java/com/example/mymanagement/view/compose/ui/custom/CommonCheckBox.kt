package com.example.mymanagement.view.compose.ui.custom

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.mymanagement.util.nonRippleClickable
import com.example.mymanagement.util.textStyle16
import com.example.mymanagement.view.compose.ui.theme.Black
import com.example.mymanagement.view.compose.ui.theme.Gray

@Composable
fun CommonCheckBox(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = textStyle16(),
    isEnable: Boolean = true,
    onCheckedChange: (Boolean) -> Unit,
) {
    val check = remember {
        mutableStateOf(false)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.nonRippleClickable {
            if (isEnable) {
                check.value = check.value.not()
                onCheckedChange(check.value)
            }
        }
    ) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(com.example.mymanagement.R.raw.check_box)
        )
        val animatable = rememberLottieAnimatable()

        LottieAnimation(
            composition = composition,
            progress = { animatable.progress },
            modifier = Modifier.size(30.dp)
        )
        Text(text = text, style = textStyle.copy(color = if (isEnable) Black else Gray))

        LaunchedEffect(check.value) {
            if (check.value) {
                animatable.animate(
                    composition,
                    clipSpec = LottieClipSpec.Progress(0.3f, 0.95f),
                    speed = 2.5f
                )
            } else {
                animatable.snapTo(composition, 0f)
            }
        }
    }
}