package com.example.uriseednavermap.data.model

data class RegionInfo(
    val region: Array<String> = emptyArray(),
    val keyword: String = "",
    val selected_region: String = "",
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RegionInfo

        if (!region.contentEquals(other.region)) return false

        return true
    }

    override fun hashCode(): Int {
        return region.contentHashCode()
    }
}
