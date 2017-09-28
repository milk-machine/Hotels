package com.milkmachine.hotels.di

import android.app.Application
import android.content.Context
import com.milkmachine.hotels.data.HotelsRepository
import com.milkmachine.hotels.data.HotelsRepositoryImpl
import com.milkmachine.hotels.data.network.NetworkApi
import com.milkmachine.hotels.data.network.NetworkApiProvider
import toothpick.smoothie.module.SmoothieApplicationModule

class DataModule(application: Application) : SmoothieApplicationModule(application) {

    init {
        bind(Context::class.java).toInstance(application)
        bind(HotelsRepository::class.java).to(HotelsRepositoryImpl::class.java)
        bind(NetworkApi::class.java).toProvider(NetworkApiProvider::class.java)
    }
}