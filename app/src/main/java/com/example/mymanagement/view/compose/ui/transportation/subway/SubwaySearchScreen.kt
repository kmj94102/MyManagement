package com.example.mymanagement.view.compose.ui.transportation.subway

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mymanagement.R
import com.example.mymanagement.database.entity.StationItem
import com.example.mymanagement.util.*
import com.example.mymanagement.view.compose.ui.custom.CommonCheckBox
import com.example.mymanagement.view.compose.ui.custom.CommonHeader
import com.example.mymanagement.view.compose.ui.custom.CommonLottie
import com.example.mymanagement.view.compose.ui.custom.SearchTextField
import com.example.mymanagement.view.compose.ui.navigation.NavScreen
import com.example.mymanagement.view.compose.ui.theme.Blue
import com.example.mymanagement.view.compose.ui.theme.Gray
import com.example.mymanagement.view.compose.ui.theme.Green
import com.example.mymanagement.view.compose.ui.theme.White
import com.example.mymanagement.view.compose.ui.transportation.subway.arrival_info.ArrivalInfoContainer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SubwaySearchScreen(
    onBackClick: () -> Unit,
    viewModel: SubwaySearchViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = false
    )
    val scope = rememberCoroutineScope()

    val stations = viewModel.stationItems.collectAsState(initial = emptyList()).value
    val arrivalInfoList = viewModel.arrivalInfoMap.collectAsState(initial = listOf()).value

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetBackgroundColor = White,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            ArrivalInfoContainer(
                list = arrivalInfoList,
                onHide = {
                    scope.launch {
                        sheetState.hide()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 13.dp, bottom = 23.dp, start = 20.dp, end = 20.dp)
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
        ) {
            // 해더 영역
            CommonHeader(
                title = NavScreen.SubwaySearch.item.title,
                onBackClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(2.dp))
            // 바디 영역
            SubwaySearchBody(
                list = stations,
                departure = viewModel.departure.value,
                arrival = viewModel.arrival.value,
                isProgress = viewModel.isProgress.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onStationClick = {
                    viewModel.fetchRealtimeStationArrivals(it)
                    scope.launch {
                        sheetState.show()
                    }
                },
                onUpdate = { viewModel.updateFavorite(it) },
                onDepartureClick = {
                    viewModel.setDeparture(it)
                },
                onArrivalClick = {
                    viewModel.setArrival(it)
                },
                onChangeClick = {
                    viewModel.swapDepartureAndArrival()
                }
            )
            // 풋터 영역
            SubwaySearchFooter(
                modifier = Modifier.fillMaxWidth(),
                onSearch = {
                    viewModel.searchStationsByStationName(it)
                },
                allOrFavoriteChangeListener = {
                    viewModel.searchStationsAllOrFavorite(it)
                }
            )
        }
    }

    BackHandler(enabled = sheetState.isVisible) {
        scope.launch {
            sheetState.hide()
        }
    }
}

@Composable
fun SubwaySearchBody(
    modifier: Modifier = Modifier,
    isProgress: Boolean,
    list: List<StationItem>,
    departure: StationItem?,
    arrival: StationItem?,
    onStationClick: (String) -> Unit,
    onUpdate: (StationItem) -> Unit,
    onDepartureClick: (StationItem) -> Unit,
    onArrivalClick: (StationItem) -> Unit,
    onChangeClick: () -> Unit
) {
    if (list.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
        ) {
            if (isProgress) {
                CircularProgressIndicator()
            } else {
                CommonLottie(rawRes = R.raw.empty)
                Text(
                    text = "지하철 역 정보가 없습니다.\n검색어 확인 후 이용해주세요.",
                    textAlign = TextAlign.Center,
                    style = textStyle16().copy(Gray)
                )
            }
        }
    } else {
        if (departure != null || arrival != null) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
                    .border(1.dp, Blue, RoundedCornerShape(6.dp))
            ) {
                val (leftCircle, rightCircle, dashedDivider,
                    txtDeparture, txtArrival, btnChange, button) = createRefs()

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(44.dp, 63.dp)
                        .clip(RoundedCornerShape(topEnd = 6.dp, bottomEnd = 6.dp))
                        .background(if (arrival != null && departure != null) Blue else Gray)
                        .nonRippleClickable { }
                        .constrainAs(button) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(26.dp)
                        .border(1.dp, Blue, CircleShape)
                        .nonRippleClickable { onChangeClick() }
                        .constrainAs(btnChange) {
                            start.linkTo(parent.start)
                            end.linkTo(button.start)
                            bottom.linkTo(parent.bottom, 12.dp)
                        }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_change),
                        contentDescription = null,
                        tint = Blue,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Text(
                    text = departure?.stationName ?: "",
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
                    text = arrival?.stationName ?: "",
                    style = textStyle16B(),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.constrainAs(txtArrival) {
                        centerVerticallyTo(btnChange)
                        start.linkTo(btnChange.end, 10.dp)
                        end.linkTo(button.start, 10.dp)
                        width = Dimension.fillToConstraints
                    }
                )

                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .border(1.dp, Blue, CircleShape)
                        .constrainAs(leftCircle) {
                            centerHorizontallyTo(txtDeparture)
                            top.linkTo(parent.top, 10.dp)
                        }
                )

                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Blue)
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

            }
        }

        LazyColumn(
            contentPadding = PaddingValues(top = 15.dp, bottom = 50.dp),
            modifier = modifier
        ) {
            items(list.size) {
                StationItemContainer(
                    item = list[it],
                    departure = departure,
                    arrival = arrival,
                    onClick = { item ->
                        onStationClick(item.stationName)
                    },
                    onFavoriteClick = { item ->
                        onUpdate(item)
                    },
                    onDepartureClick = onDepartureClick,
                    onArrivalClick = onArrivalClick
                )
            }
        }
    }
}

@Composable
fun StationItemContainer(
    item: StationItem,
    departure: StationItem?,
    arrival: StationItem?,
    onClick: (StationItem) -> Unit,
    onFavoriteClick: (StationItem) -> Unit,
    onDepartureClick: (StationItem) -> Unit,
    onArrivalClick: (StationItem) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(item) }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 15.dp, bottom = 6.dp)
        ) {
            Text(text = item.stationName, style = textStyle16B())
            Spacer(modifier = Modifier.width(10.dp))
            item.lineNames.split(",").forEach {
                StationLineContainer(
                    stationLine = it
                )
                Spacer(modifier = Modifier.width(5.dp))
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Card(
                shape = CircleShape,
                border = BorderStroke(
                    1.dp,
                    if (departure?.stationName == item.stationName) Green else Gray
                ),
                modifier = Modifier
                    .size(30.dp)
                    .nonRippleClickable { onDepartureClick(item) }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "출발",
                        style = textStyle12B().copy(color = if (departure?.stationName == item.stationName) Green else Gray),
                    )
                }
            }
            Card(
                shape = CircleShape,
                border = BorderStroke(
                    1.dp,
                    if (arrival?.stationName == item.stationName) Green else Gray
                ),
                modifier = Modifier
                    .size(30.dp)
                    .nonRippleClickable { onArrivalClick(item) }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "도착",
                        style = textStyle12B().copy(color = if (arrival?.stationName == item.stationName) Green else Gray),
                    )
                }
            }
            Image(
                painter = painterResource(id = if (item.isFavorite) R.drawable.ic_star else R.drawable.ic_star_empty),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .nonRippleClickable {
                        onFavoriteClick(item)
                    }
            )
        }
        Divider(color = Gray, modifier = Modifier.padding(top = 15.dp))
    }
}

@Composable
fun StationLineContainer(
    stationLine: String,
    onClick: () -> Unit = {}
) {
    val lineInfo = SubwayLine.getSubwayLineByCode(stationLine)
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(lineInfo.color))
            .nonRippleClickable { onClick() }
    ) {
        Text(
            text = lineInfo.lineName,
            style = textStyle12B().copy(color = White),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun SubwaySearchFooter(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    allOrFavoriteChangeListener: (Boolean) -> Unit
) {
    Card(
        backgroundColor = White,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        modifier = modifier
            .fillMaxWidth()
            .shadow(blurRadius = 3.dp, borderRadius = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 23.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_top),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(6.dp))
            SearchTextField(
                hint = "지하철 역 이름",
                onSearch = onSearch,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(modifier = Modifier.padding(start = 5.dp)) {
                CommonCheckBox(
                    text = "즐겨 찾기만",
                    onCheckedChange = allOrFavoriteChangeListener
                )
                Spacer(modifier = Modifier.width(30.dp))
                CommonCheckBox(
                    text = "특정 호선만",
                    onCheckedChange = {
                    }
                )
            }
        }
    }
}