package com.example.mymanagement.view.compose.ui.custom

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymanagement.view.compose.ui.theme.Black
import com.example.mymanagement.view.compose.ui.theme.Green
import com.example.mymanagement.view.compose.ui.theme.White
import com.example.mymanagement.util.textStyle12B

/**
 * 공용 버튼
 * @param modifier Modifier
 * @param text 버튼 텍스트
 * @param textColor 텍스트 색상
 * @param backgroundColor 버튼 색상
 * @param icon 텍스트 좌측 아이콘
 * @param onClick 버튼 클릭 리스너
 * **/
@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = White,
    backgroundColor: Color = Green,
    icon: (@Composable () -> Unit)? = null,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        elevation = null,
        shape = RoundedCornerShape(6.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = modifier.defaultMinSize(minHeight = 42.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            icon?.let { 
                it.invoke()
                Spacer(modifier = Modifier.width(5.dp))
            }
            Text(
                text = text,
                style = textStyle12B().copy(fontSize = 14.sp, color = textColor)
            )
        }
    }
}

/**
 * 공용 아웃 라인 버튼
 * @param modifier Modifier
 * @param text 버튼 텍스트
 * @param textColor 텍스트 색상
 * @param backgroundColor 버튼 색상
 * @param borderColor 테두리 색상
 * @param icon 텍스트 좌측 아이콘
 * @param onClick 버튼 클릭 리스너
 * **/
@Composable
fun CommonOutLineButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Black,
    backgroundColor: Color = White,
    borderColor: Color = Black,
    icon: (@Composable () -> Unit)? = null,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        elevation = null,
        modifier = modifier
            .defaultMinSize(minHeight = 42.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            icon?.let {
                it.invoke()
                Spacer(modifier = Modifier.width(5.dp))
            }
            Text(
                text = text,
                style = textStyle12B().copy(fontSize = 14.sp, color = textColor)
            )
        }
    }
}