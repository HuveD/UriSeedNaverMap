package com.example.uriseednavermap.data.repository

import com.example.uriseednavermap.data.remote.KakaoSearchService
import com.naver.maps.geometry.LatLng
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@InstallIn(SingletonComponent::class)
@Module
class MapRepository @Inject constructor(
    val kakaoSearchService: KakaoSearchService
) {
    suspend fun searchPlace(query: String, position: LatLng) = flow {
        emit(
            kakaoSearchService.searchPlace(
                query = query,
                x = position.longitude,
                y = position.latitude,
            ),
        )
    }
}