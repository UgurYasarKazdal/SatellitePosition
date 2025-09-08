package com.uyk.satellite.detail.data

object SatelliteDetailCache {
    private val cache = mutableMapOf<String, SatelliteDetail>()

    fun get(id: String): SatelliteDetail? = cache[id]

    fun putIfAbsent(id: String, detail: SatelliteDetail) {
        if (!cache.containsKey(id)) {
            cache[id] = detail
        }
    }
}