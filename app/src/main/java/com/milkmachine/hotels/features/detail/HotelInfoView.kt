package com.milkmachine.hotels.features.detail

import com.milkmachine.hotels.data.dto.HotelDetailedInfo
import com.milkmachine.hotels.features.base.BaseView

interface HotelInfoView : BaseView {

    fun showHotelInfo(hotelInfo: HotelDetailedInfo)
}