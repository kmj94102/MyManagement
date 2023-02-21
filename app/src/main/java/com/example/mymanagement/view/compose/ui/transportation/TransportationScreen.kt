package com.example.mymanagement.view.compose.ui.transportation

import androidx.annotation.DrawableRes
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.mymanagement.R
import com.example.mymanagement.view.compose.ui.custom.CommonLottie
import com.example.mymanagement.view.compose.ui.theme.Black
import com.example.mymanagement.view.compose.ui.theme.Blue
import com.example.mymanagement.view.compose.ui.theme.Orange
import com.example.mymanagement.view.compose.ui.theme.White
import com.example.mymanagement.util.textStyle12
import com.example.mymanagement.util.textStyle16B
import com.example.mymanagement.util.textStyle24B

/** 교통 화면 **/
@Composable
fun TransportationScreen(
    goToSearchSubway: () -> Unit,
    goToSearchBusStation: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        TransportationHeader(
            goToSearchSubway = goToSearchSubway,
            goToSearchBusStation = goToSearchBusStation,
            modifier = Modifier
        )
        TransportationBody(modifier = Modifier)
    }
}

/** 교통 화면 헤더 **/
@Composable
fun TransportationHeader(
    goToSearchSubway: () -> Unit,
    goToSearchBusStation: () -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .background(Orange)
    ) {
        Text(
            text = "교통",
            style = textStyle24B().copy(color = White),
            modifier = Modifier.padding(top = 22.dp, start = 20.dp)
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TransportationSelectButton(
                image = R.drawable.ic_bus,
                text = "버스\n시간 조회",
                modifier = Modifier.weight(1f)
            ) {
                goToSearchBusStation()
            }
            TransportationSelectButton(
                image = R.drawable.ic_subway,
                text = "지하철\n시간 조회",
                modifier = Modifier.weight(1f)
            ) {
                goToSearchSubway()
            }
        } // Row

    } // Column
}

/** 교통 선택 버튼 **/
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TransportationSelectButton(
    @DrawableRes image: Int,
    text: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, White),
        backgroundColor = White.copy(alpha = 0.3f),
        elevation = 0.dp,
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = image),
                contentDescription = text,
                tint = White,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = text,
                style = textStyle16B().copy(color = White),
                textAlign = TextAlign.Center
            )
        } // Row
    } // Card
}

/** 교통 화면 바디 **/
@Composable
fun TransportationBody(
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 25.dp)
        ) {
            Text(
                text = "즐겨찾기",
                style = textStyle16B()
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = "add",
                modifier = Modifier.size(24.dp)
            )
        } // Row

        // 수정 예정
        CommonLottie(
            rawRes = R.raw.empty
        )

        LazyColumn(
            contentPadding = PaddingValues(top = 8.dp, bottom = 20.dp),
            modifier = Modifier.padding(top = 10.dp)
        ) {
            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    TransportationFavorite(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "1234\n5678", style = textStyle16B())
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    TransportationFavorite(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "1234", style = textStyle16B())
                        Text(text = "(1234567890))", style = textStyle12())
                    }
                }
            }
        } // LazyColumn
    } // Column
}

/** 교통 즐겨찾기 아이템 **/
@Composable
fun TransportationFavorite(
    modifier: Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, Black),
        modifier = modifier
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (head, leftHall, rightHall, icon, contents) = createRefs()

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .constrainAs(head) {
                        top.linkTo(parent.top)
                        expandHorizontally()
                    }
                    .background(Blue)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = "favorite",
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .padding(vertical = 4.dp)
                        .size(12.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "10:00 - 12:00",
                    style = textStyle12(),
                    modifier = Modifier.padding(end = 10.dp)
                )
            } // Row

            Canvas(
                modifier = Modifier
                    .size(9.dp, 9.dp)
                    .constrainAs(leftHall) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, (-4.5).dp)
                    }
            ) {
                drawArc(
                    color = Black,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                )
            } // Canvas

            Canvas(
                modifier = Modifier
                    .size(9.dp, 9.dp)
                    .constrainAs(rightHall) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end, (-4.5).dp)
                    }
            ) {
                drawArc(
                    color = Black,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                )
            } // Canvas

            Image(
                painter = painterResource(id = R.drawable.ic_bus),
                contentDescription = "bus",
                modifier = Modifier
                    .size(40.dp)
                    .constrainAs(icon) {
                        top.linkTo(head.bottom, 7.dp)
                        bottom.linkTo(parent.bottom, 7.dp)
                        start.linkTo(parent.start, 10.dp)
                    }
            )

            Column(
                modifier = Modifier.constrainAs(contents) {
                    start.linkTo(icon.end, 10.dp)
                    top.linkTo(head.bottom)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, 10.dp)
                    width = Dimension.fillToConstraints
                }
            ) {
                content()
            }
        } // ConstraintLayout
    } // Card
}

@Preview
@Composable
fun PreviewTransportationScreen() {
    TransportationScreen(
        goToSearchBusStation = {},
        goToSearchSubway = {}
    )
}