package com.milkmachine.hotels.data

import com.milkmachine.hotels.data.dto.HotelDetailedInfo
import com.milkmachine.hotels.data.dto.HotelShortInfo
import com.milkmachine.hotels.data.mappers.toHotelDetailedInfo
import com.milkmachine.hotels.data.mappers.toHotelShortInfo
import com.milkmachine.hotels.data.network.InternetUnavailableException
import com.milkmachine.hotels.data.network.NetworkApi
import com.milkmachine.hotels.data.network.models.HotelResponse
import com.milkmachine.hotels.utils.NetworkStatusChecker
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HotelsRepositoryImpl @Inject constructor(private val networkApi: NetworkApi,
                                               private val networkStatusChecker: NetworkStatusChecker
) : HotelsRepository {

    override fun getHotelsList(): Single<List<HotelShortInfo>> =
            networkApi.getHotelsList()
                    .withInternetStatusCheck()
                    .flatMapObservable { Observable.fromIterable(it) }
                    .map(HotelResponse::toHotelShortInfo)
                    .toList()

    override fun getHotelDetailedInfo(hotelId: Long): Single<HotelDetailedInfo> =
            networkApi.getHotel(hotelId)
                    .withInternetStatusCheck()
                    .map(HotelResponse::toHotelDetailedInfo)

    private fun <T : Any> Single<T>.withInternetStatusCheck() =
            networkStatusChecker.isNetworkAvailable
                    .flatMap { isAvailable ->
                        if (isAvailable)
                            this
                        else
                            Single.error(InternetUnavailableException())
                    }
}