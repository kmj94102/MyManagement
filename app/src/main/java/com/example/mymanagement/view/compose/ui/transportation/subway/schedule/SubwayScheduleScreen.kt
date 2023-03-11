package com.example.mymanagement.view.compose.ui.transportation.subway.schedule

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mymanagement.R
import com.example.mymanagement.util.nonRippleClickable
import com.example.mymanagement.util.textStyle16B
import com.example.mymanagement.util.textStyle24B
import com.example.mymanagement.view.compose.ui.custom.CommonHeader
import com.example.mymanagement.view.compose.ui.custom.CommonTableHeader
import com.example.mymanagement.view.compose.ui.theme.Black
import com.example.mymanagement.view.compose.ui.theme.Gray
import com.example.mymanagement.view.compose.ui.theme.Green
import com.example.mymanagement.view.compose.ui.theme.White
import com.example.network.model.SubwayScheduleInfo

@Composable
fun SubwayScheduleScreen(
    current: String,
    prev: String,
    next: String,
    onBackClick: () -> Unit,
    viewModel: SubwayScheduleViewModel = hiltViewModel()
) {

    val isFavorite = viewModel.isFavorite.collectAsState(initial = false).value

    Column(modifier = Modifier.fillMaxSize()) {
        CommonHeader(
            title = current,
            onBackClick = onBackClick,
            tailIcons = {
                Image(
                    painter = painterResource(id = if (isFavorite) R.drawable.ic_star else R.drawable.ic_star_empty),
                    contentDescription = null,
                    modifier = Modifier.nonRippleClickable {
                        viewModel.toggleSubwayStationStatus()
                    }
                )
            }
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(7.dp),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp)
        ) {
            Text(
                text = "평일",
                style = textStyle16B().copy(
                    color = if (viewModel.week.value == SubwayScheduleViewModel.WeekDay) Green else Gray
                ),
                modifier = Modifier.nonRippleClickable {
                    viewModel.changeWeek(SubwayScheduleViewModel.WeekDay)
                }
            )
            Box(
                modifier = Modifier
                    .size(1.dp, 15.dp)
                    .background(Gray)
            )
            Text(
                text = "토요일",
                style = textStyle16B().copy(
                    color = if (viewModel.week.value == SubwayScheduleViewModel.WeekSaturday) Green else Gray
                ),
                modifier = Modifier.nonRippleClickable {
                    viewModel.changeWeek(SubwayScheduleViewModel.WeekSaturday)
                }
            )
            Box(
                modifier = Modifier
                    .size(1.dp, 15.dp)
                    .background(Gray)
            )
            Text(
                text = "공휴일",
                style = textStyle16B().copy(
                    color = if (viewModel.week.value == SubwayScheduleViewModel.WeekHolidays) Green else Gray
                ),
                modifier = Modifier.nonRippleClickable {
                    viewModel.changeWeek(SubwayScheduleViewModel.WeekHolidays)
                }
            )
        }

        CommonTableHeader(
            leftText = "$prev 방향",
            rightText = "$next  방향",
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(bottom = 50.dp)
        ) {
            viewModel.list.forEach {
                subwayScheduleInfoContainer(hour = it.first, list = it.second)
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.subwayScheduleInfoContainer(
    hour: String,
    list: List<SubwayScheduleInfo>
) {
    stickyHeader {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Green)
        ) {
            Text(
                text = hour,
                style = textStyle24B().copy(color = White, fontSize = 18.sp),
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(intrinsicSize = IntrinsicSize.Min)
        ) {
            SubwayScheduleTimeItem(
                list = list.filter { it.isUpLine },
                modifier = Modifier.weight(1f)
            )

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(Black)
            )

            SubwayScheduleTimeItem(
                list = list.filter { it.isUpLine.not() },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SubwayScheduleTimeItem(
    list: List<SubwayScheduleInfo>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(bottom = 10.dp)) {
        list.forEach {
            Row(modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp)) {
                Text(text = it.minute, style = textStyle16B())
                Text(
                    text = it.name,
                    style = textStyle16B().copy(color = Gray),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                )
            }
        }
    }
}
