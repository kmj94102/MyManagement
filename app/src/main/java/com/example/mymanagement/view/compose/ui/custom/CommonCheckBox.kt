package com.example.mymanagement.view.compose.ui.custom

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.example.mymanagement.util.nonRippleClickable
import com.example.mymanagement.util.textStyle16
import com.example.mymanagement.view.compose.ui.theme.Black
import com.example.mymanagement.view.compose.ui.theme.Gray
import com.example.mymanagement.view.compose.ui.theme.Green
import com.example.mymanagement.view.compose.ui.theme.White

@Composable
fun CommonCheckBox(
    text: String,
    textStyle: TextStyle = textStyle16(),
    isEnable: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
) {
    val check = remember {
        mutableStateOf(false)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.nonRippleClickable {
            if (isEnable) {
                onCheckedChange(check.value)
            }
        }
    ) {
        Checkbox(
            checked = check.value,
            onCheckedChange = {
                check.value = it
                onCheckedChange(it)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = Green,
                uncheckedColor = Gray,
                checkmarkColor = White
            ),
            enabled = isEnable
        )
        Text(text = text, style = textStyle.copy(color = if (isEnable) Black else Gray))
    }
}