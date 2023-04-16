package com.example.mymanagement.view.compose.ui.schedule.register

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mymanagement.util.nonRippleClickable
import com.example.mymanagement.util.textStyle16
import com.example.mymanagement.util.textStyle16B
import com.example.mymanagement.view.compose.ui.custom.CommonCheckBox
import com.example.mymanagement.view.compose.ui.custom.CommonHeader
import com.example.mymanagement.view.compose.ui.custom.CommonSelectBox
import com.example.mymanagement.view.compose.ui.custom.CommonTextField
import com.example.mymanagement.view.compose.ui.navigation.NavScreen
import com.example.mymanagement.view.compose.ui.schedule.bottom_sheet.DateSelectBottomSheet
import com.example.mymanagement.view.compose.ui.schedule.bottom_sheet.RepeatSettingBottomSheet
import com.example.mymanagement.view.compose.ui.theme.Green
import com.example.mymanagement.view.compose.ui.theme.White
import com.example.mymanagement.view.compose.ui.theme.Yellow
import com.example.network.model.kakao.RepeatRrule
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun RegisterScheduleScreen(
    date: String,
    onBackClick: () -> Unit,
    viewModel: RegisterScheduleViewModel = hiltViewModel()
) {
    val sheetIndex = remember {
        mutableStateOf(0)
    }
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    var isStartAt by remember {
        mutableStateOf(true)
    }

    when (viewModel.status.value) {
        RegisterScheduleViewModel.Status.Success -> {
            onBackClick()
        }
        RegisterScheduleViewModel.Status.Error -> {
        }
        else -> {}
    }

    ModalBottomSheetLayout(
        sheetContent = {
            when (sheetIndex.value) {
                1 -> {
                    DateSelectBottomSheet(
                        date = if (isStartAt) viewModel.event.value.startAt else viewModel.event.value.endAt,
                        pagerState = pagerState,
                        onDismiss = {
                            scope.launch {
                                sheetIndex.value = 0
                                sheetState.hide()
                            }
                        },
                        onSelect = { date ->
                            viewModel.contentUpdate(
                                type = if (isStartAt) RegisterScheduleViewModel.StartAt else RegisterScheduleViewModel.EndAt,
                                value = date
                            )
                        }
                    )
                }
                2 -> {
                    RepeatSettingBottomSheet(
                        initValue = viewModel.event.value.rrule,
                        onDismiss = {
                            scope.launch {
                                sheetIndex.value = 0
                                sheetState.hide()
                            }
                        },
                        onSelect = {
                            viewModel.contentUpdate(RegisterScheduleViewModel.Repeat, it)
                        }
                    )
                }
            }
        },
        sheetState = sheetState,
        sheetBackgroundColor = White,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            CommonHeader(
                title = NavScreen.RegisterSchedule.item.title,
                onBackClick = onBackClick
            )

            RegisterScheduleBody(
                viewModel = viewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onSelectDate = {
                    isStartAt = it
                    scope.launch {
                        sheetIndex.value = 1
                        pagerState.scrollToPage(0)
                        sheetState.show()
                    }
                },
                onSelectRepeat = {
                    scope.launch {
                        sheetIndex.value = 2
                        sheetState.show()
                    }
                }
            )

            Button(
                onClick = {
                    viewModel.createEvent()
                },
                elevation = null,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Green
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Green)
            ) {
                Text(text = "등록", style = textStyle16().copy(color = White))
            }
        }
    }
}

@Composable
fun RegisterScheduleBody(
    viewModel: RegisterScheduleViewModel,
    onSelectDate: (Boolean) -> Unit,
    onSelectRepeat: () -> Unit,
    modifier: Modifier = Modifier
) {
    val event = viewModel.event.value
    Column(
        modifier = modifier.padding(vertical = 28.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(text = "일정 시작", style = textStyle16B())
            RequiredDot()
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "일정 종료", style = textStyle16B())
            RequiredDot()
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            CommonSelectBox(
                text = event.startAt,
                modifier = Modifier
                    .weight(1f)
            ) {
                onSelectDate(true)
            }
            Spacer(modifier = Modifier.width(12.dp))
            CommonSelectBox(
                text = event.endAt,
                modifier = Modifier
                    .weight(1f)
            ) {
                onSelectDate(false)
            }
        }
        CommonCheckBox(
            text = "하루 종일",
            modifier = Modifier.padding(top = 5.dp, bottom = 15.dp, start = 15.dp),
            onCheckedChange = {
                viewModel.contentUpdate(
                    type = RegisterScheduleViewModel.IsAllDay,
                    value = if (it) "true" else "false"
                )
            }
        )

        Text(
            text = "반복 설정",
            style = textStyle16B(),
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(
            modifier = Modifier
                .height(8.dp)
                .padding(horizontal = 20.dp)
        )
        CommonSelectBox(
            text = RepeatRrule.rruleToUiString(viewModel.event.value.rrule),
            onClick = onSelectRepeat,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(18.dp))

        Row {
            Text(
                text = "일정 제목",
                style = textStyle16B(),
                modifier = Modifier.padding(start = 20.dp)
            )
            RequiredDot()
        }
        Spacer(modifier = Modifier.height(8.dp))
        CommonTextField(
            value = event.title,
            hint = "일정 제목 (최대 50자)",
            maxLength = 50,
            onValueChange = {
                viewModel.contentUpdate(
                    type = RegisterScheduleViewModel.Title,
                    value = it
                )
            },
            imeAction = ImeAction.Next,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "일정 설명",
            style = textStyle16B(),
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        CommonTextField(
            value = event.description,
            hint = "일정 설명 (최대 500자)",
            maxLength = 500,
            onValueChange = {
                viewModel.contentUpdate(
                    type = RegisterScheduleViewModel.Description,
                    value = it
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
    }
}

@Composable
fun RequiredDot() {
    Box(
        modifier = Modifier
            .padding(start = 7.dp, top = 3.dp)
            .size(7.dp)
            .clip(CircleShape)
            .background(Green)
    )
}