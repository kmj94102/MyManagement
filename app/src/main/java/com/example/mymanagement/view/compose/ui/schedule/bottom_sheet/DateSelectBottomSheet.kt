package com.example.mymanagement.view.compose.ui.schedule.bottom_sheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mymanagement.util.textStyle16
import com.example.mymanagement.view.compose.ui.custom.CommonButton
import com.example.mymanagement.view.compose.ui.custom.CommonOutLineButton
import com.example.mymanagement.view.compose.ui.custom.SelectSpinner
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DateSelectBottomSheet(
    date: String,
    pagerState: PagerState,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    val yearList = (2000..2030).map { it.toString() }
    val monthList = (1..12).map { it.toString().padStart(2, '0') }
    val dayList = remember {
        mutableStateOf((1..28).map { it.toString().padStart(2, '0') })
    }
    val hourList = (0..23).map { it.toString().padStart(2, '0') }
    val minList = (0..59).map { it.toString().padStart(2, '0') }

    val year = date.substring(0, 4)
    val month = date.substring(5, 7)
    val day = date.substring(8, 10)
    val hour = date.substring(11, 13)
    val min = date.substring(14, 16)

    val yearState = rememberPagerState()
    val monthState = rememberPagerState()
    val dayState = rememberPagerState()
    val hourState = rememberPagerState()
    val minState = rememberPagerState()

    val scope = rememberCoroutineScope()

    LaunchedEffect(yearState.currentPage) {
        dayList.value = getDayList(yearList[yearState.currentPage], monthList[monthState.currentPage])
    }
    LaunchedEffect(monthState.currentPage) {
        dayList.value = getDayList(yearList[yearState.currentPage], monthList[monthState.currentPage])
    }

    HorizontalPager(
        pageCount = 2,
        state = pagerState,
        userScrollEnabled = false,
//        modifier = Modifier.height(308.dp)
    ) {
        when (it) {
            0 -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "날짜 선택",
                        style = textStyle16(),
                        modifier = Modifier.padding(top = 27.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = Modifier.padding(top = 5.dp, bottom = 12.dp)
                    ) {
                        SelectSpinner(
                            selectList = yearList,
                            initValue = year,
                            state = yearState
                        )
                        SelectSpinner(
                            selectList = monthList,
                            initValue = month,
                            state = monthState
                        )
                        SelectSpinner(
                            selectList = dayList.value,
                            initValue = day,
                            state = dayState
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 23.dp, start = 20.dp, end = 20.dp)
                    ) {
                        CommonOutLineButton(
                            text = "취소",
                            modifier = Modifier.weight(1f)
                        ) {
                            onDismiss()
                        }
                        CommonButton(
                            text = "다음",
                            modifier = Modifier.weight(1f)
                        ) {
                            scope.launch {
                                pagerState.scrollToPage(1)
                            }
                        }
                    }
                }
            }
            1 -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "시간 선택",
                        style = textStyle16(),
                        modifier = Modifier.padding(top = 27.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = Modifier.padding(top = 5.dp, bottom = 12.dp)
                    ) {
                        SelectSpinner(
                            selectList = hourList,
                            initValue = hour,
                            state = hourState
                        )
                        SelectSpinner(
                            selectList = minList,
                            initValue = min,
                            state = minState
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 23.dp, start = 20.dp, end = 20.dp)
                    ) {
                        CommonOutLineButton(
                            text = "이전",
                            modifier = Modifier.weight(1f)
                        ) {
                            scope.launch {
                                pagerState.scrollToPage(0)
                            }
                        }
                        CommonButton(
                            text = "다음",
                            modifier = Modifier.weight(1f)
                        ) {
                            onSelect("${yearList[yearState.currentPage]}.${monthList[monthState.currentPage]}.${dayList.value[dayState.currentPage]} ${hourList[hourState.currentPage]}:${minList[minState.currentPage]}")
                            onDismiss()
                        }
                    }
                }
            }
        }
    }
}

fun getDayList(year: String, month: String): List<String> {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year.toInt())
        set(Calendar.MONTH, month.toInt() - 1)
    }

    return (1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        .map {
            it.toString().padStart(2, '0')
        }
}