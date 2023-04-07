package com.example.mymanagement.view.compose.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mymanagement.util.getToday
import com.example.mymanagement.util.gridItems
import com.example.mymanagement.util.nonRippleClickable
import com.example.mymanagement.util.textStyle16B
import com.example.mymanagement.view.compose.ui.custom.model.CalendarItem
import com.example.mymanagement.view.compose.ui.theme.*
import java.util.*

@Composable
fun CustomCalendar(
    today: String,
    calendarInfo: List<CalendarItem>,
    selectDate: String,
    onSelectChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxWidth()) {
        WeekLabel()
        Spacer(modifier = Modifier.height(5.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            gridItems(
                data = calendarInfo,
                columnCount = 7,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) { item, _ ->
                if (item.date.isEmpty()) {
                    Box(modifier = Modifier.size(35.dp))
                } else {
                    DateCard(
                        calendarItem = item,
                        selectDate = selectDate,
                        today = today,
                        onClick = onSelectChange
                    )
                }
            }
        }
    }
}

/** 요일 라벨 **/
@Composable
fun WeekLabel() {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        listOf("일", "월", "화", "수", "목", "금", "토").forEach {
            val color = when (it) {
                "일" -> Red
                "토" -> Blue
                else -> White
            }

            Box(modifier = Modifier.width(35.dp)) {
                Text(
                    text = it,
                    style = textStyle16B().copy(color = color),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun DateCard(
    calendarItem: CalendarItem,
    selectDate: String,
    today: String,
    onClick: (String) -> Unit
) {
    val color = when {
        calendarItem.dayOfWeek == "일" || calendarItem.isHoliday -> Red
        calendarItem.dayOfWeek == "토" -> Blue
        else -> White
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(35.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(
                color = if (calendarItem.detailDate == selectDate) Beige else Green
            )
            .border(
                width = 1.dp,
                color = if (today == calendarItem.detailDate) Beige else Green,
                shape = RoundedCornerShape(5.dp)
            )
            .nonRippleClickable {
                onClick(calendarItem.detailDate)
            }
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = calendarItem.date,
            style = textStyle16B().copy(color = color, fontWeight = FontWeight.Medium),
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.padding(top = 2.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(5.dp)
                    .clip(CircleShape)
                    .background(Orange)
            )
            Box(
                modifier = Modifier
                    .size(5.dp)
                    .clip(CircleShape)
                    .background(Orange)
            )
            Box(
                modifier = Modifier
                    .size(5.dp)
                    .clip(CircleShape)
                    .background(Orange)
            )
            Icon(
                painter = painterResource(id = com.example.mymanagement.R.drawable.ic_plus_small),
                contentDescription = null,
                tint = White,
                modifier = Modifier.size(5.dp)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
    }
}