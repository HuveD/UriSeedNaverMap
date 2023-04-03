package com.example.uriseednavermap.data.model

data class ResultSet<T>(
    val meta: Meta = Meta(), val documents: List<T> = emptyList(),
)
