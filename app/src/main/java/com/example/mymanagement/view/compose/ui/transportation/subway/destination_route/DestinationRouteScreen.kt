package com.example.mymanagement.view.compose.ui.transportation.subway.destination_route

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mymanagement.R
import com.example.mymanagement.util.*
import com.example.mymanagement.view.compose.ui.custom.CommonHeader
import com.example.mymanagement.view.compose.ui.navigation.NavScreen
import com.example.mymanagement.view.compose.ui.theme.Gray
import com.example.mymanagement.view.compose.ui.theme.Green
import com.example.mymanagement.view.compose.ui.theme.White
import com.example.network.model.RouteItem
import com.example.network.model.SubwayRouteInfo

@Composable
fun DestinationRouteScreen(
    startStation: String,
    endStation: String,
    onBackClick: () -> Unit,
    viewModel: DestinationRouteViewModel = hiltViewModel()
) {
    val routeInfo = viewModel.routeInfo.collectAsState(initial = SubwayRouteInfo()).value

    Column(modifier = Modifier.fillMaxSize()) {

        CommonHeader(title = NavScreen.SubwayDestinationRoute.item.title) {
            onBackClick()
        }

        DestinationRouteCard(
            startStation = startStation,
            endStation = endStation,
            routeInfo = routeInfo,
            time = viewModel.time,
            week = viewModel.week.value,
            isFavorite = viewModel.isFavorite.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp),
            onChangeClick = {

            },
            onWeekChange = {
                viewModel.updateWeek(it)
            },
            onFavoriteChange = {
                viewModel.toggleFavoriteStatus(startStation, endStation)
            }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 15.dp)
        ) {
            Text(text = "출발", style = textStyle12())
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = routeInfo.deptTime, style = textStyle24B().copy(color = Green))
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "도착", style = textStyle12())
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = routeInfo.arrivalTime, style = textStyle24B().copy(color = Green))
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = if (routeInfo.transferCount == 0) "환승 없음" else "${routeInfo.transferCount}회 환승",
                style = textStyle16().copy(Gray)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 50.dp)
        ) {
            item {
                DestinationRouteInfoList(
                    list = routeInfo.list,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun DestinationRouteCard(
    startStation: String,
    endStation: String,
    routeInfo: SubwayRouteInfo,
    time: String,
    week: String,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onChangeClick: () -> Unit,
    onWeekChange: (String) -> Unit,
    onFavoriteChange: () -> Unit
) {
    val transparentWhite = White.copy(alpha = .5f)

    Card(
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, Green),
        modifier = modifier
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (leftCircle, rightCircle, dashedDivider, txtDeparture, txtArrival,
                btnChange, weekSelector, favorite, timeInfo, fee, background) = createRefs()

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(26.dp)
                    .border(1.dp, Green, CircleShape)
                    .nonRippleClickable { onChangeClick() }
                    .constrainAs(btnChange) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top, 26.dp)
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_change),
                    contentDescription = null,
                    tint = Green,
                    modifier = Modifier.size(16.dp)
                )
            }

            Text(
                text = startStation,
                style = textStyle16B(),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(txtDeparture) {
                    centerVerticallyTo(btnChange)
                    start.linkTo(parent.start, 10.dp)
                    end.linkTo(btnChange.start, 10.dp)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = endStation,
                style = textStyle16B(),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(txtArrival) {
                    centerVerticallyTo(btnChange)
                    start.linkTo(btnChange.end, 10.dp)
                    end.linkTo(parent.end, 10.dp)
                    width = Dimension.fillToConstraints
                }
            )

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .border(1.dp, Green, CircleShape)
                    .constrainAs(leftCircle) {
                        centerHorizontallyTo(txtDeparture)
                        top.linkTo(parent.top, 10.dp)
                    }
            )

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Green)
                    .constrainAs(rightCircle) {
                        centerHorizontallyTo(txtArrival)
                        top.linkTo(parent.top, 10.dp)
                    }
            )

            Canvas(
                modifier = Modifier.constrainAs(dashedDivider) {
                    centerVerticallyTo(leftCircle)
                    start.linkTo(leftCircle.end, 5.dp)
                    end.linkTo(rightCircle.start, 5.dp)
                    width = Dimension.fillToConstraints
                }
            ) {
                drawLine(
                    color = Gray,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f), 0f)
                )
            }

            Box(
                modifier = Modifier
                    .background(Green)
                    .constrainAs(background) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(btnChange.bottom, 10.dp)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp),
                modifier = Modifier.constrainAs(weekSelector) {
                    start.linkTo(parent.start, 10.dp)
                    top.linkTo(background.top, 10.dp)
                }
            ) {
                Text(
                    text = "평일",
                    style = textStyle16B().copy(
                        color = if (week == DestinationRouteViewModel.WeekDay) White else transparentWhite
                    ),
                    modifier = Modifier.nonRippleClickable {
                        onWeekChange(DestinationRouteViewModel.WeekDay)
                    }
                )
                Box(
                    modifier = Modifier
                        .size(1.dp, 15.dp)
                        .background(transparentWhite)
                )
                Text(
                    text = "토요일",
                    style = textStyle16B().copy(
                        color = if (week == DestinationRouteViewModel.WeekSaturday) White else transparentWhite
                    ),
                    modifier = Modifier.nonRippleClickable {
                        onWeekChange(DestinationRouteViewModel.WeekSaturday)
                    }
                )
                Box(
                    modifier = Modifier
                        .size(1.dp, 15.dp)
                        .background(transparentWhite)
                )
                Text(
                    text = "공휴일",
                    style = textStyle16B().copy(
                        color = if (week == DestinationRouteViewModel.WeekHolidays) White else transparentWhite
                    ),
                    modifier = Modifier.nonRippleClickable {
                        onWeekChange(DestinationRouteViewModel.WeekHolidays)
                    }
                )
            }

            Icon(
                painter = painterResource(id = if (isFavorite) R.drawable.ic_star else R.drawable.ic_star_empty),
                contentDescription = null,
                tint =  if (isFavorite) Color(0xFFFFD36E) else White,
                modifier = Modifier
                    .constrainAs(favorite) {
                        top.linkTo(background.top, 7.dp)
                        end.linkTo(parent.end, 10.dp)
                    }
                    .nonRippleClickable { onFavoriteChange() }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.constrainAs(timeInfo) {
                    start.linkTo(weekSelector.start)
                    top.linkTo(weekSelector.bottom, 5.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                }
            ) {
                Text(text = "시간", style = textStyle12().copy(White))
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = time, style = textStyle16B().copy(White))
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_bottom),
                    contentDescription = null,
                    tint = White
                )
            }

            Text(
                text = routeInfo.fee,
                style = textStyle16B().copy(color = White),
                modifier = Modifier.constrainAs(fee) {
                    end.linkTo(parent.end, 10.dp)
                    bottom.linkTo(parent.bottom, 12.dp)
                }
            )
        }
    }
}

@Composable
fun DestinationRouteInfoList(
    list: List<RouteItem>,
    modifier: Modifier = Modifier
) {
    var isFirst = true
    var color = Green

    list.forEachIndexed { index, item ->
        if (isFirst) {
            color = Color(SubwayLine.getLineColorByRouteCode(item.lineCode))
            Box(modifier = modifier.height(31.dp)) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 10.dp)
                        .size(width = 10.dp, height = 2.dp)
                        .background(color)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(color)
                    ) {
                        Text(
                            text = item.lineCode,
                            style = if (item.lineCode.length > 2) textStyle12B().copy(
                                color = White,
                                fontSize = 10.sp
                            ) else textStyle12B().copy(White),
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            modifier = Modifier.padding(horizontal = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(text = item.stationName, style = textStyle16B())
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = item.time, style = textStyle16())
                }
            }
            isFirst = false
        } else if (index == list.size - 1) {
            Box(modifier = modifier.height(31.dp)) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 10.dp)
                        .size(width = 10.dp, height = 2.dp)
                        .background(color)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.BottomCenter)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(color)
                    ) {
                        Text(
                            text = "하차",
                            style = textStyle12B().copy(White),
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(text = item.stationName, style = textStyle16B())
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = item.time, style = textStyle16())
                }
            }
            isFirst = true
        } else if (item.transferLocation != null) {
            val strokeWidth = LocalDensity.current.run { 10.dp.toPx() }

            Box(modifier = modifier.height(76.dp)) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 10.dp)
                        .size(width = 10.dp, height = 2.dp)
                        .background(color)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(top = 1.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(color)
                    ) {
                        Text(text = "하차", style = textStyle12B().copy(White))
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(text = item.stationName, style = textStyle16B())
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = item.time, style = textStyle16())
                }
                Canvas(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 4.dp, start = 15.dp)
                        .size(10.dp, 37.dp)
                ) {
                    drawLine(
                        color = Gray,
                        start = Offset(0f, 0f),
                        end = Offset(0f, size.height),
                        strokeWidth = strokeWidth,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f), 0f)
                    )
                }
            }
            isFirst = true
        } else {
            Box(modifier = modifier) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 10.dp)
                        .size(10.dp, 53.dp)
                        .background(color)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp)
                        .align(Alignment.Center)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(20.dp)
                            .background(color)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = item.stationName, style = textStyle16B())
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = item.time, style = textStyle16())
                }
            }
        }
    }
}



