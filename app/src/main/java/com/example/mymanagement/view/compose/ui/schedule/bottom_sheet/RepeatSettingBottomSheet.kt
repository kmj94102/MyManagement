package com.example.mymanagement.view.compose.ui.schedule.bottom_sheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mymanagement.util.getToday
import com.example.mymanagement.util.textStyle16
import com.example.mymanagement.view.compose.ui.custom.CommonButton
import com.example.mymanagement.view.compose.ui.custom.CommonOutLineButton
import com.example.mymanagement.view.compose.ui.custom.CommonRadio
import com.example.mymanagement.view.compose.ui.custom.SelectSpinner
import com.example.network.model.kakao.RepeatRrule

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RepeatSettingBottomSheet(
    initValue: String,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    val checkValue = remember {
        mutableStateOf(RepeatRrule.getRepeatState(initValue))
    }
    val date = RepeatRrule.getUntilTile(initValue)
    val yearList = (2000..2030).map { it.toString() }
    val monthList = (1..12).map { it.toString().padStart(2, '0') }
    val dayList = remember {
        mutableStateOf((1..28).map { it.toString().padStart(2, '0') })
    }

    val year = date.ifEmpty { getToday("yyyy.MM.dd") }.substring(0, 4)
    val month = date.ifEmpty { getToday("yyyy.MM.dd") }.substring(5, 7)
    val day = date.ifEmpty { getToday("yyyy.MM.dd") }.substring(8, 10)

    val yearState = rememberPagerState()
    val monthState = rememberPagerState()
    val dayState = rememberPagerState()

    val scope = rememberCoroutineScope()

    LaunchedEffect(yearState.currentPage) {
        dayList.value =
            getDayList(yearList[yearState.currentPage], monthList[monthState.currentPage])
    }
    LaunchedEffect(monthState.currentPage) {
        dayList.value =
            getDayList(yearList[yearState.currentPage], monthList[monthState.currentPage])
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "반복 설정",
            style = textStyle16(),
            modifier = Modifier
                .padding(top = 27.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.padding(horizontal = 20.dp)) {
            CommonRadio(
                text = "반복 안 함",
                check = checkValue.value == RepeatRrule.NoRepeat,
                onCheckedChange = {
                    checkValue.value = RepeatRrule.NoRepeat
                },
                modifier = Modifier.weight(1f)
            )
            CommonRadio(
                text = "매일",
                check = checkValue.value == RepeatRrule.Daily,
                onCheckedChange = {
                    checkValue.value = RepeatRrule.Daily
                },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.padding(horizontal = 20.dp)) {
            CommonRadio(
                text = "매주",
                check = checkValue.value == RepeatRrule.Weekly,
                onCheckedChange = {
                    checkValue.value = RepeatRrule.Weekly
                },
                modifier = Modifier.weight(1f)
            )
            CommonRadio(
                text = "매월",
                check = checkValue.value == RepeatRrule.Monthly,
                onCheckedChange = {
                    checkValue.value = RepeatRrule.Monthly
                },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.padding(horizontal = 20.dp)) {
            CommonRadio(
                text = "매년",
                check = checkValue.value == RepeatRrule.Yearly,
                onCheckedChange = {
                    checkValue.value = RepeatRrule.Yearly
                },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(23.dp))


        AnimatedVisibility(visible = checkValue.value != RepeatRrule.NoRepeat) {
            Column {
                Text(
                    text = "반복 종료일",
                    style = textStyle16(),
                    modifier = Modifier.padding(start = 20.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
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
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 23.dp)
        ) {
            CommonOutLineButton(
                text = "취소",
                modifier = Modifier.weight(1f)
            ) {
                onDismiss()
            }
            CommonButton(
                text = "확인",
                modifier = Modifier.weight(1f)
            ) {
                onSelect(
                    RepeatRrule.createRrule(
                        repeatCycle = checkValue.value,
                        until = "${yearList[yearState.currentPage]}.${monthList[monthState.currentPage]}.${dayList.value[dayState.currentPage]}"
                    )
                )
                onDismiss()
            }
        }
    }
}

