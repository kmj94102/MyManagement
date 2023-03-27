package com.example.mymanagement.view.xml.ui.transportation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymanagement.database.entity.Favorite
import com.example.mymanagement.database.entity.FavoriteEntity
import com.example.network.repository.TransportationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TransportationViewModel2 @Inject constructor(
    private val repository: TransportationRepository
): ViewModel() {

    private val sharedFlow = MutableSharedFlow<Unit>(replay = 1)

    val favoriteList: StateFlow<List<Favorite>> = sharedFlow.flatMapLatest {
        repository.fetchFavoriteList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = emptyList()
    )

    init {
        fetchFavoriteList()
    }

    fun fetchFavoriteList() {
        sharedFlow.tryEmit(Unit)
    }

}