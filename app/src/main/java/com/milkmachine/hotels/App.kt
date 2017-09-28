package com.milkmachine.hotels

import android.app.Application
import com.milkmachine.hotels.di.DataModule
import com.milkmachine.hotels.di.RootScope
import toothpick.Toothpick
import toothpick.configuration.Configuration
import toothpick.registries.FactoryRegistryLocator.setRootRegistry
import toothpick.registries.MemberInjectorRegistryLocator

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initRootScope()
    }

    private fun initRootScope() {
        Toothpick.setConfiguration(Configuration.forProduction().disableReflection())
        MemberInjectorRegistryLocator.setRootRegistry(MemberInjectorRegistry())
        setRootRegistry(FactoryRegistry())

        val appScope = Toothpick.openScope(RootScope::class.java)
        appScope.installModules(DataModule(this))
    }
}