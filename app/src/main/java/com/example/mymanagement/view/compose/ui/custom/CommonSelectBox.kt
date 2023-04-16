package com.example.mymanagement.view.compose.ui.custom

import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.mymanagement.util.nonRippleClickable
import com.example.mymanagement.util.textStyle16
import com.example.mymanagement.view.compose.ui.theme.Gray
import com.example.mymanagement.view.compose.ui.theme.Green

@Composable
fun CommonSelectBox(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = textStyle16(),
    onClick: () -> Unit,
) {
    val focusRequester = FocusRequester()
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = if (isFocused) Green else Gray,
                shape = RoundedCornerShape(6.dp)
            )
            .focusRequester(focusRequester)
            .onFocusChanged {
                isFocused = it.isFocused
            }
            .focusTarget()
            .nonRippleClickable {
                focusRequester.requestFocus()
                onClick()
            }
    ) {
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier.padding(top = 13.dp, bottom = 13.dp, start = 12.dp)
        )
    }
}