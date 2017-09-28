package com.milkmachine.hotels.features.list

import com.arellomobile.mvp.InjectViewState
import com.milkmachine.hotels.data.HotelsRepository
import com.milkmachine.hotels.data.dto.HotelShortInfo
import com.milkmachine.hotels.features.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers.io
import javax.inject.Inject

@InjectViewState
@HotelsListScope
class HotelsListPresenter @Inject constructor(private val hotelsRepository: HotelsRepository
) : BasePresenter<HotelsListView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadHotels()
    }

    private fun loadHotels(sorter: (List<HotelShortInfo>) -> List<HotelShortInfo> = byDistanceSorter) {
        disposables += hotelsRepository.getHotelsList()
                .subscribeOn(io())
                .map(sorter)
                .observeOn(mainThread())
                .withProgress()
                .subscribeBy(onSuccess = { viewState.showHotelsList(it) },
                        onError = { viewState.showError() })
    }

    fun onSortByDistancePicked() {
        loadHotels(byDistanceSorter)
    }

    fun onSortByAvailableSuitesPicked() {
        loadHotels(byAvailableSuitesSorter)
    }

    fun onHotelItemClicked(hotel: HotelShortInfo) {
        hotelsRepository.getHotelDetailedInfo(hotel.id)
                .subscribeOn(io())
                .observeOn(mainThread())
                .withProgress()
                .subscribeBy(onSuccess = { viewState.navigateToDetailInfoScreen(it) },
                        onError = { viewState.showError() })
    }

    companion object {
        private val byDistanceSorter: (List<HotelShortInfo>) -> List<HotelShortInfo> =
                { it.sortedBy(HotelShortInfo::distanceMeters) }

        private val byAvailableSuitesSorter: (List<HotelShortInfo>) -> List<HotelShortInfo> =
                { it.sortedByDescending { it.availableSuites.size } }
    }
}