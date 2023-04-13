package com.example.mymanagement.view.compose.ui.schedule.bottom_sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.mymanagement.view.compose.ui.custom.CommonRadio

@Composable
fun RepeatSettingBottomSheet(
    initValue: String,
    onDismiss: () -> Unit
) {
    val checkValue = remember {
        mutableStateOf(initValue)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        CommonRadio(
            text = "반복 안 함",
            check = checkValue.value == "",
            onCheckedChange = {

            },
        )
    }
}

