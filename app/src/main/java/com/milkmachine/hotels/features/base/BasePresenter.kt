package com.milkmachine.hotels.features.base

import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<V : BaseView> : MvpPresenter<V>() {
    protected val disposables = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    protected fun <T : Any> Single<T>.withProgress(): Single<T> =
            doOnSubscribe { viewState.showProgress() }
                    .doFinally { viewState.hideProgress() }
}