package com.milkmachine.hotels.features.detail

import com.arellomobile.mvp.InjectViewState
import com.milkmachine.hotels.data.HotelsRepository
import com.milkmachine.hotels.data.dto.HotelDetailedInfo
import com.milkmachine.hotels.features.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers.io
import javax.inject.Inject

@InjectViewState
@HotelInfoScope
class HotelInfoPresenter @Inject constructor(private val hotelInfo: HotelDetailedInfo
) : BasePresenter<HotelInfoView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showHotelInfo(hotelInfo)
    }
}