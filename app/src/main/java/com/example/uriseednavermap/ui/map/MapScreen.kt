package com.example.uriseednavermap.ui.map

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uriseednavermap.ui.theme.Purple200
import com.example.uriseednavermap.viewmodels.MapViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.*

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(viewModel: MapViewModel = viewModel()) {
    val mapUiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Text(
                modifier = Modifier
                    .clickable { viewModel.searchPlace("꽃집") }
                    .background(color = Purple200)
                    .padding(all = 16.dp),
                text = "꽃집",
            )
            Text(
                modifier = Modifier
                    .clickable { viewModel.searchPlace("농약") }
                    .background(color = Purple200)
                    .padding(all = 16.dp),
                text = "농약",
            )

        }
        NaverMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = mapUiState.cameraPositionState,
            uiSettings = mapUiState.mapUiSettings,
            properties = mapUiState.mapProperties,
            onLocationChange = { Log.i("Test", it.toString()) }
        ) {
            for (place in mapUiState.places) {
                Marker(
                    state = MarkerState(
                        position = LatLng(
                            place.y.toDouble(), place.x.toDouble()
                        )
                    ), captionText = place.place_name
                )
            }
        }
    }
}