package com.milkmachine.hotels.data.mappers

import com.milkmachine.hotels.API_URL
import com.milkmachine.hotels.data.dto.HotelDetailedInfo
import com.milkmachine.hotels.data.dto.HotelShortInfo
import com.milkmachine.hotels.data.network.models.HotelResponse

private const val ADDRESS_SEPARATOR = " "

fun HotelResponse.toHotelShortInfo(): HotelShortInfo = HotelShortInfo(
        id = id,
        name = name,
        stars = stars.toFloat(),
        distanceMeters = distance.toLong(),
        availableSuites = parseAvailableSuites(suitesAvailability))

fun HotelResponse.toHotelDetailedInfo(): HotelDetailedInfo = HotelDetailedInfo(
        id = id,
        name = name,
        stars = stars.toFloat(),
        distanceMeters = distance.toLong(),
        imageUrl = if (image != null && image.isNotEmpty()) "$API_URL$image" else "",
        availableSuites = parseAvailableSuites(suitesAvailability),
        address = addressToCamelCase(address),
        latitude = latitude!!.toString(),
        longitude = longitude!!.toString())

private fun parseAvailableSuites(availableSuites: String): List<Int> =
        availableSuites.split(':').filter(String::isNotBlank).map(String::toInt)

private fun addressToCamelCase(address: String): String =
        address.split(ADDRESS_SEPARATOR).joinToString(ADDRESS_SEPARATOR) { it.toLowerCase().capitalize() }