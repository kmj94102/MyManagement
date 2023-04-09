package com.example.mymanagement.view.compose.ui.login

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.repository.KakaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: KakaoRepository
): ViewModel() {

    private val _status : MutableState<Status> = mutableStateOf(Status.Init)
    val state = _status

    fun kakaoLogin(context: Context) = viewModelScope.launch {
        repository.kakaoLogin(context)
        _status.value = Status.Success
    }

    sealed class Status {
        object Init: Status()
        object Success: Status()
    }

}