package com.milkmachine.hotels.features.list

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.milkmachine.hotels.data.dto.HotelDetailedInfo
import com.milkmachine.hotels.data.dto.HotelShortInfo
import com.milkmachine.hotels.features.base.BaseView

interface HotelsListView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showHotelsList(hotelsList: List<HotelShortInfo>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun navigateToDetailInfoScreen(hotelInfo: HotelDetailedInfo)
}