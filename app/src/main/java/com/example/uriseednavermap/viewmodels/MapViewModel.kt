package com.example.uriseednavermap.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uriseednavermap.data.model.Place
import com.example.uriseednavermap.data.model.ResultSet
import com.example.uriseednavermap.data.repository.MapRepository
import com.example.uriseednavermap.ui.map.MapUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: MapRepository
) : ViewModel() {

    // Game UI state
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    /**
     * 장소 검색
     */
    fun searchPlace(keyword: String) {
        viewModelScope.launch {
            repository.searchPlace(keyword, uiState.value.cameraPositionState.position.target)
                .flowOn(Dispatchers.IO).catch { ResultSet<Place>() }.collect {
                    _uiState.update { mapUiState -> mapUiState.copy(places = it.documents) }
                }
        }
    }
}