package com.example.mymanagement.view.compose.ui.transportation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymanagement.database.entity.Favorite
import com.example.network.repository.TransportationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class TransportationViewModel @Inject constructor(
    private val repository: TransportationRepository
) : ViewModel() {

    private val _isProgress = mutableStateOf(false)
    val isProgress: State<Boolean> = _isProgress

    private val _favoriteList = mutableStateListOf<Favorite>()
    val favoriteList: List<Favorite> = _favoriteList

    init {
        fetchFavorite()
    }

    private fun fetchFavorite() {
        repository
            .fetchFavoriteList()
            .onStart { _isProgress.value = true }
            .onEach {
                _favoriteList.clear()
                _favoriteList.addAll(it)
            }
            .catch { it.printStackTrace() }
            .onCompletion { _isProgress.value = false }
            .launchIn(viewModelScope)
    }

}