package com.example.mymanagement.view.compose.ui.schedule

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mymanagement.util.nonRippleClickable
import com.example.mymanagement.util.textStyle24B
import com.example.mymanagement.view.compose.ui.custom.CustomCalendar
import com.example.mymanagement.view.compose.ui.schedule.bottom_sheet.YearMonthSelectBottomSheet
import com.example.mymanagement.view.compose.ui.theme.Green
import com.example.mymanagement.view.compose.ui.theme.White
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScheduleScreen() {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = false
    )
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetBackgroundColor = White,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        modifier = Modifier.fillMaxSize(),
        sheetContent = {
            YearMonthSelectBottomSheet(
                year = "2023",
                month = "04",
                onDismiss = {
                    scope.launch {
                        sheetState.hide()
                    }
                },
                onSelect = { year, month ->
                    Log.e("++++++", "$year, $month")
                }
            )
        }
    ) {
        ScheduleHeader(
            modifier = Modifier.fillMaxWidth(),
            onYearMonthSelect = {
                scope.launch {
                    sheetState.show()
                }
            }
        )
    }
}

@Composable
fun ScheduleHeader(
    modifier: Modifier = Modifier,
    onYearMonthSelect: () -> Unit
) {
    val year = remember {
        mutableStateOf(2023)
    }
    val month = remember {
        mutableStateOf(4)
    }
    val selectDate = remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .background(Green)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 22.dp)
        ) {
            Text(text = "일정", style = textStyle24B().copy(color = White))
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "2023.02",
                style = textStyle24B().copy(color = White),
                modifier = Modifier.nonRippleClickable { onYearMonthSelect() }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                painter = painterResource(id = com.example.mymanagement.R.drawable.ic_arrow_bottom),
                contentDescription = null,
                tint = White,
                modifier = Modifier.nonRippleClickable {
                    onYearMonthSelect()
                }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        CustomCalendar(
            year = year,
            month = month,
            selectDate = selectDate,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
    }
}