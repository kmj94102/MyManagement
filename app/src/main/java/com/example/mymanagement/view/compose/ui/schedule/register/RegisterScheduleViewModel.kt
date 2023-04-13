package com.example.mymanagement.view.compose.ui.schedule.register

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymanagement.view.compose.ui.navigation.NavScreen
import com.example.network.model.kakao.EventCreate
import com.example.network.repository.KakaoRepository
import com.example.network.util.convertToRFC5545
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterScheduleViewModel @Inject constructor(
    private val kakaoRepository: KakaoRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val date = savedStateHandle.get<String>(NavScreen.RegisterSchedule.Date) ?: "2023.01.01"
    private val _event = mutableStateOf(
        EventCreate(title = "", startAt = "$date 08:00", endAt = "$date 10:00")
    )
    val event: State<EventCreate> = _event

    private val _status: MutableState<Status> = mutableStateOf(Status.Init)
    val status: State<Status> = _status

    fun contentUpdate(
        type: String,
        value: String
    ) {
        _event.value = when(type) {
            StartAt -> {
                _event.value.copy(startAt = value)
            }
            EndAt -> {
                _event.value.copy(endAt = value)
            }
            IsAllDay -> {
                _event.value.copy(isAllDay = value == "true")
            }
            Replay -> {
                _event.value.copy()
            }
            Title -> {
                _event.value.copy(title = value)
            }
            else -> {
                _event.value.copy(description = value)
            }
        }
    }

    fun createEvent() = viewModelScope.launch {
        kakaoRepository.createEvent(
            eventCreate = _event.value.copy(
                startAt = convertToRFC5545(_event.value.startAt),
                endAt = convertToRFC5545(_event.value.endAt)
            ),
            onSuccess = {
                _status.value = Status.Success
            },
            onError = {
                _status.value = Status.Error
            }
        )
    }

    sealed class Status {
        object Init: Status()
        object Success: Status()
        object Error: Status()
    }

    companion object {
        const val StartAt = "startAt"
        const val EndAt = "endAt"
        const val IsAllDay = "isAllDay"
        const val Replay = "replay"
        const val Title = "title"
        const val Description = "description"
    }

}