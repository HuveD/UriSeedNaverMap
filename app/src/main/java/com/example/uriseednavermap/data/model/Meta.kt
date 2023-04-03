package com.example.uriseednavermap.data.model

data class Meta(
    val total_count: Int = 0,
    val pageable_count: Int = 0,
    val is_end: Boolean = true,
    val same_name: RegionInfo = RegionInfo()
)
