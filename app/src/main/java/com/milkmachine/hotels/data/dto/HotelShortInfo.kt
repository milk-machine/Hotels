package com.milkmachine.hotels.data.dto

data class HotelShortInfo(val id: Long,
                          val name: String,
                          val stars: Float,
                          val distanceMeters: Long,
                          val availableSuites: List<Int>)