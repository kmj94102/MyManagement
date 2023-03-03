package com.example.mymanagement.view.compose.ui.transportation.bus.route

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mymanagement.R
import com.example.mymanagement.util.nonRippleClickable
import com.example.mymanagement.util.textStyle16B
import com.example.mymanagement.view.compose.ui.custom.CommonHeader
import com.example.mymanagement.view.compose.ui.theme.Black
import com.example.mymanagement.view.compose.ui.theme.Gray
import com.example.mymanagement.view.compose.ui.theme.Green
import com.example.network.model.BusStopRouteItem

@Composable
fun BusStopRouteScreen(
    number: String,
    nodeId: String,
    onBackClick: () -> Unit,
    viewModel: BusStopRouteViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxSize()) {
        CommonHeader(
            title = number,
            onBackClick = onBackClick
        )

        LazyColumn(
            contentPadding = PaddingValues(
                top = 18.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 50.dp
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(viewModel.list) {
                BusStopRouteInfoContainer(
                    info = it,
                    nodeId = nodeId,
                    onClick = {},
                    onFavoriteClick = { item ->
                        viewModel.toggleBusFavoriteStatus(busNumber = number, item = item)
                    }
                )
            }
        }

    }
}

@Composable
fun BusStopRouteInfoContainer(
    info: BusStopRouteItem,
    nodeId: String,
    onClick: (BusStopRouteItem) -> Unit,
    onFavoriteClick: (BusStopRouteItem) -> Unit
) {
    val isCurrentNode = info.nodeId == nodeId
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .nonRippleClickable { onClick(info) }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(1.dp, 11.dp)
                    .background(if (info.isStartNode.not()) Gray else Color.Transparent)
            )
            Box(
                modifier = Modifier
                    .size(17.dp)
                    .border(
                        width = if (info.isEndNode || info.isStartNode || isCurrentNode) 17.dp else 1.dp,
                        color = if (isCurrentNode) Green else Gray,
                        shape = CircleShape
                    )
            )
            Box(
                modifier = Modifier
                    .size(1.dp, 11.dp)
                    .background(if (info.isEndNode.not()) Gray else Color.Transparent)
            )
        } // Column

        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = "${info.nodeName} (${info.nodeNumber})",
            style = textStyle16B().copy(color = if (isCurrentNode) Green else Black),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(15.dp))
        Image(
            painter = painterResource(id = if (info.isFavorite) R.drawable.ic_star else R.drawable.ic_star_empty),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .nonRippleClickable {
                    onFavoriteClick(info)
                }
        )
    } // Row
}