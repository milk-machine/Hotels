package com.milkmachine.hotels.data.network.models

import com.google.gson.annotations.SerializedName

data class HotelResponse(val id: Long,
                 val name: String,
                 val address: String,
                 val stars: Double,
                 val distance: Double,
                 val image: String?,
                 @SerializedName("suites_availability")
                 val suitesAvailability: String,
                 @SerializedName("lat")
                 val latitude: Double?,
                 @SerializedName("lon")
                 val longitude: Double?)