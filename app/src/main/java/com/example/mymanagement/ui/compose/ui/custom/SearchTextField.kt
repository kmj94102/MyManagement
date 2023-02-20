package com.example.mymanagement.ui.compose.ui.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymanagement.ui.compose.ui.theme.Black
import com.example.mymanagement.ui.compose.ui.theme.Gray
import com.example.mymanagement.ui.compose.ui.theme.Green
import com.example.mymanagement.ui.compose.ui.theme.White
import com.example.mymanagement.util.nonRippleClickable
import com.example.mymanagement.util.textStyle16

/**
 * 검색 창
 * @param hint 검색 창 힌트
 * @param onSearch 검색 리스너
 * @param modifier modifier
 * **/
@Composable
fun SearchTextField(
    hint: String,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var value by remember {
        mutableStateOf("")
    }

    CommonTextField(
        value = value,
        hint = hint,
        onValueChange = {
                        value = it
        },
        onSearch = onSearch,
        leadingIcon = {
            Icon(
                painter = painterResource(id = com.example.mymanagement.R.drawable.ic_search),
                contentDescription = "search",
                modifier = Modifier
                    .size(24.dp)
                    .nonRippleClickable {
                        onSearch(value)
                    }
            )
        },
        imeAction = ImeAction.Search,
        modifier = modifier
    )
}

@Composable
@Preview
fun PreviewSearchTextField() {
    Column(modifier = Modifier.fillMaxWidth()) {
        SearchTextField(
            hint = "인근 지역 검색",
            onSearch = {
                println(it)
            },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
    }

}