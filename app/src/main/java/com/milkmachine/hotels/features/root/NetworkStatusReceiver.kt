package com.milkmachine.hotels.features.root

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.milkmachine.hotels.features.base.NetworkStatusListener
import com.milkmachine.hotels.utils.NetworkStatusChecker

class NetworkStatusReceiver(private val consumer: NetworkStatusListener) : BroadcastReceiver() {
    private var networkStatusChecker: NetworkStatusChecker? = null

    override fun onReceive(context: Context, intent: Intent?) {
        if (networkStatusChecker == null)
            networkStatusChecker = NetworkStatusChecker(context)

        consumer.onNetworkStatusChanged(networkStatusChecker!!.checkNetworkAvailability)
    }
}