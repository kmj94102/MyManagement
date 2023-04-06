package com.example.mymanagement.view.compose.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.example.mymanagement.util.textStyle24B
import com.example.mymanagement.view.compose.ui.theme.Gray
import com.google.accompanist.pager.*
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SelectSpinner(
    selectList: List<String>,
    state: PagerState,
    initValue: String,
) {
    Box {
        VerticalPager(
            count = selectList.size,
            contentPadding = PaddingValues(vertical = 60.dp),
            state = state,
            modifier = Modifier
                .size(90.dp, 180.dp)
        ) { index ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(90.dp, 60.dp)
            ) {
                Text(
                    text = selectList[index],
                    style = textStyle24B().copy(fontSize = 22.sp),
                    modifier = Modifier.graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(index).absoluteValue
                        alpha = lerp(
                            start = 0.2f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(top = 60.dp)
                .size(90.dp, 1.dp)
                .background(Gray)
        )
        Box(
            modifier = Modifier
                .padding(top = 120.dp)
                .size(90.dp, 1.dp)
                .background(Gray)
        )
    }

    LaunchedEffect(Unit) {
        val index = selectList.indexOf(initValue)
        state.scrollToPage(if (index == -1) 0 else index)
    }
}