package com.example.uriseednavermap.data.remote

import com.example.uriseednavermap.BuildConfig
import com.example.uriseednavermap.data.model.Place
import com.example.uriseednavermap.data.model.ResultSet
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoSearchService {
    @GET("v2/local/search/keyword.json")
    suspend fun searchPlace(
        @Header("Authorization") auth: String = BuildConfig.kakaoApiKey,
        @Query("query") query: String,
        @Query("x") x: Double = 127.098,
        @Query("y") y: Double = 37.511,
        @Query("size") size: Int = 15,
        @Query("radius") radius: Int? = null,
    ): ResultSet<Place>
}