package com.example.mymanagement.view.compose.ui.transportation

import androidx.annotation.DrawableRes
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mymanagement.R
import com.example.mymanagement.database.entity.Favorite
import com.example.mymanagement.database.entity.FavoriteEntity
import com.example.mymanagement.view.compose.ui.custom.CommonLottie
import com.example.mymanagement.util.textStyle12
import com.example.mymanagement.util.textStyle16
import com.example.mymanagement.util.textStyle16B
import com.example.mymanagement.util.textStyle24B
import com.example.mymanagement.view.compose.ui.theme.*

/** 교통 화면 **/
@Composable
fun TransportationScreen(
    goToSearchSubway: () -> Unit,
    goToSearchBusStation: () -> Unit,
    viewModel: TransportationViewModel = hiltViewModel()
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
        TransportationBody(
            list = viewModel.favoriteList,
            modifier = Modifier
        )
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
                modifier = Modifier.weight(1f),
                onClick = goToSearchBusStation
            )
            TransportationSelectButton(
                image = R.drawable.ic_subway,
                text = "지하철\n시간 조회",
                modifier = Modifier.weight(1f),
                onClick = goToSearchSubway
            )
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
    list: List<Favorite>,
    modifier: Modifier = Modifier
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
                painter = painterResource(id = R.drawable.ic_setting),
                contentDescription = "setting",
                modifier = Modifier.size(24.dp)
            )
        } // Row

        if (list.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                CommonLottie(
                    rawRes = R.raw.empty,
                )
                Text(
                    text = "즐겨찾기를 추가해주세요",
                    style = textStyle12().copy(color = Gray)
                )
            }
        } else {
            LazyVerticalGrid(
                contentPadding = PaddingValues(top = 8.dp, bottom = 20.dp),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(top = 10.dp)
            ) {
                items(list.size) {
                    TransportationFavorite(
                        favorite = list[it],
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } // LazyColumn
        }
    } // Column
}

/** 교통 즐겨찾기 아이템 **/
@Composable
fun TransportationFavorite(
    favorite: Favorite,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, Black),
        modifier = modifier.height(87.dp)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (head, leftHall, rightHall, contents) = createRefs()

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .constrainAs(head) {
                        top.linkTo(parent.top)
                        expandHorizontally()
                    }
                    .background(getBackgroundColor(favorite.type))
            ) {
                Icon(
                    painter = painterResource(id = getFavoriteIconRes(favorite.type)),
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier
                        .padding(start = 7.dp, top = 4.dp, bottom = 4.dp)
                        .size(24.dp)
                )

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = if (favorite.time.trim() != "-") favorite.time else "",
                    style = textStyle12().copy(White),
                    modifier = Modifier.padding(end = 7.dp)
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

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.constrainAs(contents) {
                    start.linkTo(parent.start, 10.dp)
                    top.linkTo(head.bottom)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, 10.dp)
                    width = Dimension.fillToConstraints
                }
            ) {
                FavoriteContents(
                    type = favorite.type,
                    text = favorite.name,
                )
            }
        } // ConstraintLayout
    } // Card
}

private fun getBackgroundColor(type: String) = when(type) {
    FavoriteEntity.TypeSubway, FavoriteEntity.TypeSubwayDestination -> Green
    else -> Blue
}

fun getFavoriteIconRes(type: String) = when (type) {
    FavoriteEntity.TypeSubway, FavoriteEntity.TypeSubwayDestination -> R.drawable.ic_subway
    FavoriteEntity.TypeBusStop -> R.drawable.ic_busstop
    else -> R.drawable.ic_bus
}

@Composable
fun FavoriteContents(
    type: String,
    text: String,
    modifier: Modifier = Modifier
) {
    when (type) {
        FavoriteEntity.TypeSubway, FavoriteEntity.TypeBusStop -> {
            Text(
                text = text,
                style = textStyle16B(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier
                    .fillMaxWidth()
            )
        }
        FavoriteEntity.TypeSubwayDestination -> {
            val infoList = text.split(FavoriteEntity.Separator)
            if (infoList.size != 2) {
                Text(
                    text = text,
                    style = textStyle16B(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier
                        .fillMaxWidth()
                )
            } else {
                val spanStyle = SpanStyle(fontWeight = FontWeight.Normal, fontSize = 12.sp)
                Text(
                    buildAnnotatedString {
                        withStyle(style = spanStyle) {
                            append("출발")
                        }
                        append("  ${infoList[0]}\n")
                        withStyle(style = spanStyle) {
                            append("도착")
                        }
                        append("  ${infoList[1]}")
                    },
                    style = textStyle16B(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier
                        .fillMaxWidth()
                )
            }
        }
        else -> {
            val infoList = text.split(FavoriteEntity.Separator)
            if (infoList.size != 2) {
                Text(
                    text = text,
                    style = textStyle16B(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier
                        .fillMaxWidth()
                )
            } else {
                val spanStyle = SpanStyle(fontWeight = FontWeight.Normal, fontSize = 12.sp)
                Text(
                    buildAnnotatedString {
                        append(infoList[0].plus("\n"))
                        withStyle(style = spanStyle) {
                            append(infoList[1])
                        }
                    },
                    style = textStyle16B(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTransportationScreen() {
    TransportationScreen(
        goToSearchBusStation = {},
        goToSearchSubway = {}
    )
}