package com.example.uriseednavermap.ui.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uriseednavermap.viewmodels.MapViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(viewModel: MapViewModel = viewModel()) {
    val mapUiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            // Permission Accepted: Do something
            Log.d("ExampleScreen", "PERMISSION GRANTED")

        } else {
            // Permission Denied: Do something
            Log.d("ExampleScreen", "PERMISSION DENIED")
        }
    }

    SideEffect {
        checkPermission(
            context = context,
            permission = Manifest.permission.ACCESS_FINE_LOCATION,
            onDenied = { launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION) },
        )
    }
    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) -> {
            // Some works that require permission
            Log.d("ExampleScreen", "Code requires permission")
        }
        else -> {
            // Asking for permission
            SideEffect {
                launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        TabRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            selectedTabIndex = mapUiState.tabIndex,
            backgroundColor = Color(0xffffffff),
            contentColor = Color(0xffE16B87)
        ) {
            for ((index, tab) in PlaceTab.values().withIndex()) {
                Tab(selected = PlaceTab.values().indexOf(tab) == index,
                    onClick = { viewModel.searchPlace(tab) },
                    text = {
                        Text(
                            modifier = Modifier.padding(vertical = 16.dp),
                            text = tab.keyword(),
                            color = Color(0xFF000000)
                        )
                    })
            }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            NaverMap(modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.25f),
                locationSource = rememberFusedLocationSource(),
                cameraPositionState = mapUiState.cameraPositionState,
                uiSettings = mapUiState.mapUiSettings,
                properties = mapUiState.mapProperties,
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
                onMapLoaded = { viewModel.searchPlace(PlaceTab.values().first()) }) {
                for (place in mapUiState.places) {
                    Marker(state = MarkerState(
                        position = LatLng(
                            place.y.toDouble(), place.x.toDouble()
                        )
                    ), captionText = place.place_name, onClick = {
                        // 마커 클릭시 지도 이동
                        moveMap(
                            cameraPositionState = mapUiState.cameraPositionState,
                            target = it.position
                        )
                        false
                    })
                }
                if (mapUiState.places.isNotEmpty()) fitBoundsMap(
                    cameraPositionState = mapUiState.cameraPositionState,
                    bounds = LatLngBounds.from(mapUiState.places.map {
                        LatLng(
                            it.y.toDouble(), it.x.toDouble()
                        )
                    }),
                )
            }

            if (mapUiState.places.isNotEmpty()) Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .align(Alignment.BottomCenter)
            )
        }

        if (mapUiState.places.isNotEmpty()) Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                )
        ) {
            LazyColumn {
                items(mapUiState.places) {
                    PlaceItemView(place = it)
                }
            }
        }
    }
}

/**
 * 퍼미션 체크
 */
private fun checkPermission(
    context: Context,
    permission: String,
    onGranted: (() -> Unit)? = null,
    onDenied: (() -> Unit)? = null,
) = when (ContextCompat.checkSelfPermission(context, permission)) {
    PackageManager.PERMISSION_GRANTED -> onGranted?.let { it() }
    else -> onDenied?.let { it() }
}

/**
 * 줌 변경
 */
@OptIn(ExperimentalNaverMapApi::class)
private fun setZoom(cameraPositionState: CameraPositionState, zoom: Double) {
    cameraPositionState.move(CameraUpdate.zoomTo(zoom))
}

/**
 * 지도 이동
 */
@OptIn(ExperimentalNaverMapApi::class)
private fun moveMap(cameraPositionState: CameraPositionState, target: LatLng) {
    MainScope().launch {
        cameraPositionState.animate(CameraUpdate.scrollTo(target))
    }
}


/**
 * 지도 이동
 */
@OptIn(ExperimentalNaverMapApi::class)
private fun fitBoundsMap(cameraPositionState: CameraPositionState, bounds: LatLngBounds) {
    MainScope().launch {
        cameraPositionState.animate(CameraUpdate.fitBounds(bounds))
    }
}