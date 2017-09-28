package com.milkmachine.hotels.data.dto

import android.os.Parcel
import android.os.Parcelable

data class HotelDetailedInfo(val id: Long,
                             val name: String,
                             val stars: Float,
                             val distanceMeters: Long,
                             val imageUrl: String,
                             val availableSuites: List<Int>,
                             val address: String,
                             val latitude: String,
                             val longitude: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readFloat(),
            parcel.readLong(),
            parcel.readString(),
            mutableListOf<Int>().apply { parcel.readList(this, Int::class.java.classLoader) },
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeFloat(stars)
        parcel.writeLong(distanceMeters)
        parcel.writeString(imageUrl)
        parcel.writeList(availableSuites)
        parcel.writeString(address)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HotelDetailedInfo> {
        override fun createFromParcel(parcel: Parcel): HotelDetailedInfo {
            return HotelDetailedInfo(parcel)
        }

        override fun newArray(size: Int): Array<HotelDetailedInfo?> {
            return arrayOfNulls(size)
        }
    }
}