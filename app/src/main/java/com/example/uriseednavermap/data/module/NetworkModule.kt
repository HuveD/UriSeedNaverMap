package com.example.uriseednavermap.data.module

import com.example.uriseednavermap.data.remote.KakaoSearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideSearchService(): KakaoSearchService {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://dapi.kakao.com").build().create(KakaoSearchService::class.java)
    }
}