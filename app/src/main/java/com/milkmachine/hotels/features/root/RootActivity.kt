package com.milkmachine.hotels.features.root

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.milkmachine.hotels.R
import com.milkmachine.hotels.features.base.ActionBarProvider
import com.milkmachine.hotels.features.base.NetworkStatusListener
import com.milkmachine.hotels.features.list.HotelsListController
import kotlinx.android.synthetic.main.activity_root.*

class RootActivity : AppCompatActivity(), NetworkStatusListener, ActionBarProvider {
    private lateinit var router: Router
    private val networkStatusReceiver: NetworkStatusReceiver = NetworkStatusReceiver(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        setSupportActionBar(toolbar)
        setupRouter(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        registerInternetStatusReceiver()
    }

    override fun onPause() {
        unregisterReceiver(networkStatusReceiver)
        super.onPause()
    }

    private fun registerInternetStatusReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkStatusReceiver, intentFilter)
    }

    private fun setupRouter(savedInstanceState: Bundle?) {
        router = Conductor.attachRouter(this, controller_container, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(HotelsListController()))
        }
    }

    override val actionBar: ActionBar
        get() = supportActionBar!!

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }

    override fun onNetworkStatusChanged(isAvailable: Boolean) {
        if (isAvailable)
            internet_indicator.visibility = View.GONE
        else
            internet_indicator.visibility = View.VISIBLE
    }
}