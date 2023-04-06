package com.example.uriseednavermap.ui.map

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import com.example.uriseednavermap.data.model.Place
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings

data class MapUiState(
    val mapUiSettings: MapUiSettings = MapUiSettings(
        isZoomControlEnabled = false,
        isLogoClickEnabled = false,
        isLocationButtonEnabled = true,
        isScaleBarEnabled = false,
        logoMargin = PaddingValues(0.dp),
    ),
    val mapProperties: MapProperties = MapProperties(
        maxZoom = 17.0,
        minZoom = 5.0,
        locationTrackingMode = LocationTrackingMode.Follow
    ),
    val cameraPositionState: CameraPositionState = CameraPositionState(),
    val places: List<Place> = emptyList(),
    val tabIndex: Int = 0
)
