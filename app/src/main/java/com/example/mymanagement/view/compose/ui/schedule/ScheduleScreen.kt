package com.example.mymanagement.view.compose.ui.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mymanagement.R
import com.example.mymanagement.util.nonRippleClickable
import com.example.mymanagement.util.textStyle12
import com.example.mymanagement.util.textStyle24B
import com.example.mymanagement.view.compose.ui.custom.CustomCalendar
import com.example.mymanagement.view.compose.ui.custom.model.CalendarItem
import com.example.mymanagement.view.compose.ui.schedule.bottom_sheet.YearMonthSelectBottomSheet
import com.example.mymanagement.view.compose.ui.theme.Gray
import com.example.mymanagement.view.compose.ui.theme.Green
import com.example.mymanagement.view.compose.ui.theme.Red
import com.example.mymanagement.view.compose.ui.theme.White
import com.example.network.model.kakao.Schedule
import com.example.network.util.convertToDateTime
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScheduleScreen(
    goToRegister: (String) -> Unit,
    viewModel: ScheduleViewModel = hiltViewModel()
) {
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
                year = viewModel.year.value,
                month = viewModel.month.value,
                onDismiss = {
                    scope.launch {
                        sheetState.hide()
                    }
                },
                onSelect = { year, month ->
                    viewModel.setYearMonth(year = year, month = month)
                }
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            ScheduleHeader(
                modifier = Modifier.fillMaxWidth(),
                onYearMonthSelect = {
                    scope.launch {
                        sheetState.show()
                    }
                },
                viewModel = viewModel
            )
            Spacer(modifier = Modifier.height(15.dp))
            ScheduleBody(
                item = viewModel.selectCalendarItem,
                goToRegister = goToRegister
            )
        }
    }
}

@Composable
fun ScheduleHeader(
    modifier: Modifier = Modifier,
    onYearMonthSelect: () -> Unit,
    viewModel: ScheduleViewModel
) {
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
                text = "${viewModel.year.value}.${viewModel.month.value}",
                style = textStyle24B().copy(color = White),
                modifier = Modifier.nonRippleClickable { onYearMonthSelect() }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_bottom),
                contentDescription = null,
                tint = White,
                modifier = Modifier.nonRippleClickable {
                    onYearMonthSelect()
                }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        CustomCalendar(
            today = viewModel.today,
            calendarInfo = viewModel.calendarInfo,
            selectDate = viewModel.selectDate.value,
            onSelectChange = viewModel::onSelectChange,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
    }
}

@Composable
fun ScheduleBody(
    item: CalendarItem?,
    goToRegister: (String) -> Unit
) {
    if (item == null) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                buildAnnotatedString {
                    append("${item.date.padStart(2, '0')} (${item.dayOfWeek}) ")
                    if (item.isHoliday) {
                        withStyle(style = SpanStyle(color = Gray, fontSize = 16.sp)) {
                            append(item.dateInfo)
                        }
                    }
                },
                style = textStyle24B()
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .nonRippleClickable {
                        goToRegister(item.detailDate)
                    }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(top = 10.dp, bottom = 50.dp)
        ) {
            item.scheduleList.forEach {
                item {
                    ScheduleCard(it)
                }
            }
        }
    }
}

@Composable
fun ScheduleCard(schedule: Schedule) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Red, RoundedCornerShape(8.dp))
    ) {
        Text(
            text = "${
                convertToDateTime(
                    schedule.startAt,
                    "HH:mm"
                )
            } ~ ${convertToDateTime(schedule.endAt, "HH.mm")}",
            style = textStyle12(),
            modifier = Modifier.padding(top = 10.dp, start = 10.dp)
        )
        Text(
            text = schedule.title,
            style = textStyle24B().copy(fontSize = 20.sp, color = Red),
            modifier = Modifier.padding(top = 4.dp, start = 10.dp, bottom = 10.dp)
        )
    }
}