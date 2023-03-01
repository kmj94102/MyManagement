package com.example.mymanagement.view.compose.ui.transportation.subway.arrival_info

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.mymanagement.util.SubwayLine
import com.example.mymanagement.util.textStyle12B
import com.example.mymanagement.view.compose.ui.custom.CommonButton
import com.example.mymanagement.view.compose.ui.custom.CommonOutLineButton
import com.example.mymanagement.view.compose.ui.theme.Gray
import com.example.mymanagement.view.compose.ui.theme.Red
import com.example.mymanagement.view.compose.ui.theme.White
import com.example.mymanagement.view.compose.ui.transportation.subway.StationLineContainer
import com.example.network.model.SubwayArrival

@Composable
fun ArrivalInfoContainer(
    list: List<SubwayArrival>,
    onHide: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectIndex = remember {
        mutableStateOf(0)
    }
    val selectColor = remember {
        mutableStateOf(White)
    }

    LaunchedEffect(list) {
        selectIndex.value = 0
    }

    if (list.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = "지하철 정보를 가져오지 못 하였습니다.",
                style = textStyle12B().copy(color = Gray),
                modifier = Modifier.padding(top = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            CommonButton(text = "닫기") {
                onHide()
            }
        }
    } else {
        Column(modifier = modifier.fillMaxWidth()) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                list.forEachIndexed { index, arrivalInfo ->
                    if (index == 0) {
                        selectColor.value = Color(
                            SubwayLine.getSubwayLineByCode(arrivalInfo.subwayLineId).color
                        )
                    }

                    StationLineContainer(
                        stationLine = arrivalInfo.subwayLineId
                    ) {
                        selectIndex.value = index
                        selectColor.value = Color(
                            SubwayLine.getSubwayLineByCode(arrivalInfo.subwayLineId).color
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Gray)
            )

            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (prevStation, currentStation, nextStation, prevIcon, nextIcon,
                    backgroundBar, upLine, downLine, divider) = createRefs()

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(selectColor.value)
                        .constrainAs(backgroundBar) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            height = Dimension.value(20.dp)
                            width = Dimension.fillToConstraints
                            centerVerticallyTo(currentStation)
                        }
                )

                Card(
                    shape = RoundedCornerShape(30.dp),
                    border = BorderStroke(width = 4.dp, color = selectColor.value),
                    backgroundColor = White,
                    modifier = Modifier
                        .width(145.dp)
                        .constrainAs(currentStation) {
                            top.linkTo(parent.top)
                            centerHorizontallyTo(parent)
                        }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            list.getOrNull(selectIndex.value)?.currentStationName ?: "",
                            style = textStyle12B().copy(fontSize = 20.sp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
                        )
                    }
                }

                Icon(
                    painter = painterResource(id = com.example.mymanagement.R.drawable.ic_prev),
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier
                        .size(15.dp)
                        .constrainAs(prevIcon) {
                            top.linkTo(backgroundBar.top)
                            bottom.linkTo(backgroundBar.bottom)
                            start.linkTo(backgroundBar.start, 5.dp)
                        }
                )

                Text(
                    text = list.getOrNull(selectIndex.value)?.prevStationName ?: "",
                    style = textStyle12B().copy(color = White),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.constrainAs(prevStation) {
                        top.linkTo(backgroundBar.top)
                        bottom.linkTo(backgroundBar.bottom)
                        start.linkTo(prevIcon.end)
                        end.linkTo(currentStation.start, 10.dp)
                        width = Dimension.fillToConstraints
                    }
                )

                Icon(
                    painter = painterResource(id = com.example.mymanagement.R.drawable.ic_next),
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier
                        .size(15.dp)
                        .constrainAs(nextIcon) {
                            top.linkTo(backgroundBar.top)
                            bottom.linkTo(backgroundBar.bottom)
                            end.linkTo(backgroundBar.end, 5.dp)
                        }
                )

                Text(
                    text = list.getOrNull(selectIndex.value)?.nextStationName ?: "",
                    style = textStyle12B().copy(color = White),
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.constrainAs(nextStation) {
                        top.linkTo(backgroundBar.top)
                        bottom.linkTo(backgroundBar.bottom)
                        start.linkTo(currentStation.end, 10.dp)
                        end.linkTo(nextIcon.start)
                        width = Dimension.fillToConstraints
                    }
                )

                Column(
                    modifier = Modifier.constrainAs(upLine) {
                        top.linkTo(currentStation.bottom, 10.dp)
                        start.linkTo(parent.start)
                        end.linkTo(divider.start, 10.dp)
                        width = Dimension.fillToConstraints
                    }
                ) {
                    list.getOrNull(selectIndex.value)
                        ?.arrItemList
                        ?.filter { it.isUpLine }
                        ?.forEach {
                            StationArrivalInfoItem(it.arrInfo)
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                }

                Column(
                    modifier = Modifier.constrainAs(downLine) {
                        top.linkTo(currentStation.bottom, 10.dp)
                        start.linkTo(divider.end, 10.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                ) {
                    list.getOrNull(selectIndex.value)
                        ?.arrItemList
                        ?.filter { it.isUpLine.not() }
                        ?.forEach {
                            StationArrivalInfoItem(it.arrInfo)
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                }

                Box(
                    modifier = Modifier
                        .constrainAs(divider) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(currentStation.bottom, 10.dp)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.value(1.dp)
                            height = Dimension.fillToConstraints
                        }
                        .background(Gray)
                )

            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)
            ) {
                CommonOutLineButton(
                    text = "다시 조회",
                    icon = {
                        Image(
                            painter = painterResource(id = com.example.mymanagement.R.drawable.ic_refresh),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    modifier = Modifier.weight(1f)
                ) {

                }

                CommonButton(
                    text = "시간표",
                    modifier = Modifier.weight(1f)
                ) {

                }
            }
        }
    }

}

@Composable
fun StationArrivalInfoItem(
    arrInfo: String
) {
    val regex = Regex("(.*행 )(.*)")
    val result = regex.matchEntire(arrInfo)?.destructured
    result?.let { (destinationName, arrTime) ->
        Row {
            Text(
                buildAnnotatedString {
                    append(destinationName)
                    withStyle(style = SpanStyle(color = Red)) {
                        append(arrTime)
                    }
                },
                style = textStyle12B()
            )
        }
    }
}