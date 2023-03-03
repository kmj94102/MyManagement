package com.example.mymanagement.view.compose.ui.transportation.bus.arrival_info

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mymanagement.R
import com.example.mymanagement.util.*
import com.example.mymanagement.view.compose.ui.custom.CommonButton
import com.example.mymanagement.view.compose.ui.custom.CommonHeader
import com.example.mymanagement.view.compose.ui.custom.CommonLottie
import com.example.mymanagement.view.compose.ui.theme.Black
import com.example.mymanagement.view.compose.ui.theme.Blue
import com.example.mymanagement.view.compose.ui.theme.Gray
import com.example.mymanagement.view.compose.ui.theme.White
import com.example.network.model.BusEstimatedArrivalInfo
import com.example.network.model.EstimatedArrivalInfo

@Composable
fun BusStopArrivalInfoScreen(
    name: String,
    onBackClick: () -> Unit = {},
    viewModel: BusStopArrivalInfoViewModel = hiltViewModel()
) {
    val arrivalInfoList = viewModel.arrivalInfoList.collectAsState(initial = emptyList()).value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 해더 영역
        CommonHeader(
            title = name,
            onBackClick = onBackClick,
            tailIcons = {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.ic_refresh),
                        contentDescription = "다시 조회",
                        modifier = Modifier
                            .size(24.dp)
                            .nonRippleClickable {
                                viewModel.fetchEstimatedArrivalInfoList()
                            }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Image(
                        painter = painterResource(id = if (viewModel.isFavoriteStation.value) R.drawable.ic_star else R.drawable.ic_star_empty),
                        contentDescription = "",
                        modifier = Modifier.nonRippleClickable {
                            viewModel.toggleBusStopFavoriteStatus(name)
                        }
                    )
                }
            }
        )

        // 바디 영역
        if (arrivalInfoList.isEmpty()) {
            Spacer(modifier = Modifier.weight(1f))
            CommonLottie(rawRes = R.raw.empty)
            Text(
                text = "해당 정류소의 버스 정보를 불러오지 못하였습니다.",
                style = textStyle12(),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
        } else if (arrivalInfoList.size == 1) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 25.dp, bottom = 23.dp, start = 20.dp, end = 20.dp)
            ) {
                BigBusStopArrivalInfoContainer(arrivalInfoList[0])
                Spacer(modifier = Modifier.weight(1f))
                CommonButton(
                    text = "노선 보기",
                    modifier = Modifier.fillMaxWidth()
                ) {

                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                contentPadding = PaddingValues(
                    top = 25.dp,
                    bottom = 100.dp,
                    start = 20.dp,
                    end = 20.dp
                )
            ) {
                items(arrivalInfoList.size) {
                    SmallBusStopArrivalInfoContainer(
                        info = arrivalInfoList[it],
                        modifier = Modifier.fillMaxWidth(),
                        onFavoriteClick = { info ->
                            viewModel.toggleBusFavoriteStatus(info)
                        }
                    )
                }
            }
        }

    }
}

@Composable
fun SmallBusStopArrivalInfoContainer(
    info: BusEstimatedArrivalInfo,
    modifier: Modifier = Modifier,
    onFavoriteClick: (BusEstimatedArrivalInfo) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 6.dp,
        backgroundColor = Blue,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(info.busNumber, style = textStyle24B().copy(color = White))
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.ic_route),
                    contentDescription = "",
                    tint = White
                )
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(id = if (info.isFavorite) R.drawable.ic_star else R.drawable.ic_star_empty),
                    contentDescription = "",
                    modifier = Modifier.nonRippleClickable {
                        onFavoriteClick(info)
                    }
                )
            } // Row

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                    .background(White)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.padding(10.dp)
                ) {
                    info.arrInfo.forEach {
                        Text(
                            text = it,
                            style = textStyle16B(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            } // Box
        } // Column
    } // card
}

@Composable
fun BigBusStopArrivalInfoContainer(
    info: BusEstimatedArrivalInfo,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Blue),
        backgroundColor = Blue,
        modifier = modifier
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (busNumber, favorite, lottie, lottieBg, arrInfo) = createRefs()

            Text(
                text = info.busNumber,
                style = textStyle24B().copy(fontSize = 26.sp, color = White),
                modifier = Modifier.constrainAs(busNumber) {
                    centerHorizontallyTo(parent)
                    top.linkTo(parent.top, 12.dp)
                }
            )

            Image(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
                modifier = Modifier.constrainAs(favorite) {
                    centerVerticallyTo(busNumber)
                    end.linkTo(parent.end, 20.dp)
                }
            )

            Box(
                modifier = Modifier
                    .constrainAs(lottieBg) {
                        top.linkTo(busNumber.bottom, 12.dp)
                        bottom.linkTo(lottie.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.value(134.dp)
                        width = Dimension.fillToConstraints
                    }
                    .background(White)
            )

            CommonLottie(
                rawRes = R.raw.bus,
                modifier = Modifier
                    .constrainAs(lottie) {
                        centerHorizontallyTo(parent)
                        centerVerticallyTo(lottieBg)
                        height = Dimension.value(130.dp)
                    }
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.constrainAs(arrInfo) {
                    top.linkTo(lottieBg.bottom, 15.dp)
                    bottom.linkTo(parent.bottom, 15.dp)
                    centerHorizontallyTo(parent)
                }
            ) {
                info.arrInfo.forEach {
                    Text(
                        text = it,
                        style = textStyle24B().copy(color = White),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewBusStopArrivalInfoScreen() {
    BusStopArrivalInfoScreen(
        name = "테스트 정류소"
    )
}