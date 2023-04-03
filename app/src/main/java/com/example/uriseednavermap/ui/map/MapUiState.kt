package com.example.uriseednavermap.ui.map

import com.example.uriseednavermap.data.model.Place
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings

data class MapUiState(
    val mapUiSettings: MapUiSettings = MapUiSettings(
        isZoomControlEnabled = false, isLogoClickEnabled = false,
    ),
    val mapProperties: MapProperties = MapProperties(maxZoom = 17.0, minZoom = 5.0),
    val cameraPositionState: CameraPositionState = CameraPositionState(),
    val places: List<Place> = emptyList()
)
