package com.example.mymanagement.view.compose.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.repository.KakaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: KakaoRepository
) : ViewModel() {

    private val _token = mutableStateOf("1")
    val token: State<String?> = _token

    init {
        fetchToken()
    }

    private fun fetchToken() {
        repository.fetchAccessTokenFlow()
            .onEach { _token.value = it }
            .catch { _token.value = "" }
            .launchIn(viewModelScope)
    }

    fun logout() = viewModelScope.launch {
        repository.kakaoLogout()
    }
}