package com.milkmachine.hotels.data.network

import com.milkmachine.hotels.data.network.models.HotelResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkApi {

    @GET("0777.json")
    fun getHotelsList(): Single<List<HotelResponse>>

    @GET("{hotelId}.json")
    fun getHotel(@Path("hotelId") hotelId: Long): Single<HotelResponse>
}