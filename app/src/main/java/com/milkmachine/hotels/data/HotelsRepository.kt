package com.milkmachine.hotels.data

import com.milkmachine.hotels.data.dto.HotelDetailedInfo
import com.milkmachine.hotels.data.dto.HotelShortInfo
import io.reactivex.Single

interface HotelsRepository {

    fun getHotelsList(): Single<List<HotelShortInfo>>

    fun getHotelDetailedInfo(hotelId: Long): Single<HotelDetailedInfo>
}