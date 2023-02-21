package com.example.mymanagement.view.compose.ui.custom

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*

/**
 * 공용 Lottie
 * @param rawRes Lottie res
 * @param modifier Modifier
 * **/
@Composable
fun CommonLottie(
    @RawRes
    rawRes: Int,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(rawRes))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = {
            progress
        },
        modifier = modifier
    )
}