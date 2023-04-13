package com.example.mymanagement.view.compose.ui.schedule.bottom_sheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mymanagement.util.textStyle16
import com.example.mymanagement.view.compose.ui.custom.CommonButton
import com.example.mymanagement.view.compose.ui.custom.CommonOutLineButton
import com.example.mymanagement.view.compose.ui.custom.SelectSpinner
import com.example.mymanagement.view.compose.ui.theme.White

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun YearMonthSelectBottomSheet(
    year: String,
    month: String,
    onDismiss: () -> Unit,
    onSelect: (String, String) -> Unit
) {
    val yearList = (2000..2030).map { it.toString() }
    val monthList = (1..12).map { it.toString().padStart(2, '0') }

    val yearState = rememberPagerState()
    val monthState = rememberPagerState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
    ) {
        Text(
            text = "연월 선택",
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
                text = "확인",
                modifier = Modifier.weight(1f)
            ) {
                onSelect(yearList[yearState.currentPage], monthList[monthState.currentPage])
                onDismiss()
            }
        }
    }
}