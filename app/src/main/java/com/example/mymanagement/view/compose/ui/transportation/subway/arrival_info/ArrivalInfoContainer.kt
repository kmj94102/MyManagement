package com.example.mymanagement.view.compose.ui.transportation.subway.arrival_info

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.mymanagement.view.compose.ui.theme.Green
import com.example.mymanagement.view.compose.ui.theme.Red
import com.example.mymanagement.view.compose.ui.theme.White
import com.example.mymanagement.view.compose.ui.transportation.subway.StationLineContainer
import com.example.network.model.SubwayArrivalInfo

@Composable
fun ArrivalInfoContainer(
    infoMap: Map<String, List<SubwayArrivalInfo>>,
    stationName: String,
    modifier: Modifier = Modifier
) {
    val selectKey = remember {
        mutableStateOf("")
    }
    val selectColor = remember {
        mutableStateOf(White)
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            infoMap.keys.sorted().forEachIndexed { index, stationLine ->
                if (index == 0) {
                    selectKey.value = stationLine
                    selectColor.value = Color(SubwayLine.getSubwayLineByCode(stationLine).color)
                }

                StationLineContainer(
                    stationLine = stationLine
                ) {
                    selectKey.value = stationLine
                    selectColor.value = Color(SubwayLine.getSubwayLineByCode(stationLine).color)
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
                        stationName,
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
                text = infoMap[selectKey.value]?.getOrNull(0)?.prevStationId ?: "",
                style = textStyle12B().copy(color = White),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(prevStation) {
                    top.linkTo(backgroundBar.top)
                    bottom.linkTo(backgroundBar.bottom)
                    start.linkTo(prevIcon.end)
                    end.linkTo(currentStation.start, 10.dp)
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
                text = infoMap[selectKey.value]?.getOrNull(0)?.nextStationId ?: "",
                style = textStyle12B().copy(color = White),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(nextStation) {
                    top.linkTo(backgroundBar.top)
                    bottom.linkTo(backgroundBar.bottom)
                    start.linkTo(currentStation.end, 10.dp)
                    end.linkTo(nextIcon.start)
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
                infoMap[selectKey.value]
                    ?.filter { it.updnLine == "상행" || it.updnLine == "0" }
                    ?.forEach {
                        StationArrivalInfoItem(it.arrInfo, it.destinationName)
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
                infoMap[selectKey.value]
                    ?.filter { it.updnLine == "하행" || it.updnLine == "1" }
                    ?.forEach {
                        StationArrivalInfoItem(it.arrInfo, it.destinationName)
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

@Composable
fun StationArrivalInfoItem(
    arrTime: String,
    destinationName: String
) {
    Row {
        Text(
            buildAnnotatedString {
                append("${destinationName}행 ")
                withStyle(style = SpanStyle(color = Red)) {
                    append(arrTime)
                }
            },
            style = textStyle12B()
        )
    }
}