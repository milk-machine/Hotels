package com.milkmachine.hotels.features.base

interface NetworkStatusListener {
    fun onNetworkStatusChanged(isAvailable: Boolean)
}