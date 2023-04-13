package com.example.mymanagement.view.compose.ui.custom

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mymanagement.view.compose.ui.theme.Black
import com.example.mymanagement.view.compose.ui.theme.Gray
import com.example.mymanagement.view.compose.ui.theme.Green
import com.example.mymanagement.view.compose.ui.theme.White
import com.example.mymanagement.util.textStyle16

/**
 * 공용 TextField
 * @param value TextField text value
 * @param hint TextField 힌트
 * @param modifier Modifier
 * @param leadingIcon TextField 좌측 아이콘
 * @param textStyle TextField 텍스트 스타일
 * @param keyboardType 키보드 타입
 * @param imeAction ImeAction
 * @param onValueChange TextField 값 변경 리스너
 * @param onSearch 검색 리스너
 * **/
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CommonTextField(
    value: String,
    modifier: Modifier = Modifier,
    hint: String = "",
    maxLength: Int = -1,
    leadingIcon: (@Composable () -> Unit)? = null,
    textStyle: TextStyle = textStyle16(),
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    isReadOnly: Boolean = false,
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = value,
        onValueChange = {
            if (maxLength == -1 || it.length <= maxLength) {
                onValueChange(it)
            }
        },
        modifier = modifier,
        placeholder = {
            Text(text = hint, style = textStyle.copy(color = Gray))
        },
        textStyle = textStyle,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                onSearch(value)
            },
            onDone = {
                keyboardController?.hide()
            }
        ),
        maxLines = 1,
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            placeholderColor = Gray,
            textColor = Black,
            backgroundColor = White,
            cursorColor = Green,
            focusedIndicatorColor = Green,
            unfocusedIndicatorColor = Gray
        ),
        readOnly = isReadOnly,
        shape = RoundedCornerShape(6.dp)
    )
}