package com.milkmachine.hotels.utils

import android.content.Context
import android.net.ConnectivityManager
import io.reactivex.Single
import javax.inject.Inject

class NetworkStatusChecker @Inject constructor(context: Context) {
    private val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val isNetworkAvailable: Single<Boolean>
        get() = Single.just(checkNetworkAvailability)

    val checkNetworkAvailability: Boolean
        get() {
            val info = connectivityManager.activeNetworkInfo

            return info != null && info.isConnectedOrConnecting
        }
}