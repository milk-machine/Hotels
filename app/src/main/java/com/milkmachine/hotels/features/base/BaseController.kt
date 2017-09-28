package com.milkmachine.hotels.features.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.ActionBar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpDelegate
import com.bluelinelabs.conductor.Controller
import com.milkmachine.hotels.R
import com.milkmachine.hotels.di.RootScope
import kotlinx.android.synthetic.main.activity_root.*
import toothpick.Toothpick
import toothpick.config.Module

abstract class BaseController(args: Bundle? = null) : Controller(args) {
    @get:LayoutRes protected abstract val layoutId: Int
    protected abstract val scopeClass: Class<*>
    protected open val dependencyModules: Array<Module> = emptyArray()

    private val progressBar by lazy { activity?.progress_bar }

    private val mvpDelegate: MvpDelegate<*> by lazy { MvpDelegate(this) }

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)

        val scope = Toothpick.openScopes(RootScope::class.java, scopeClass)
        scope.installModules(*dependencyModules)
        Toothpick.inject(this, scope)

        mvpDelegate.onCreate(args)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View =
            inflater.inflate(layoutId, container, false)

    override fun onAttach(view: View) {
        super.onAttach(view)
        mvpDelegate.onAttach()
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        mvpDelegate.onDetach()
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        mvpDelegate.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        mvpDelegate.onDestroy()
        Toothpick.closeScope(scopeClass)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvpDelegate.onSaveInstanceState(outState)
    }

    protected fun getActionBar(): ActionBar {
        val actionBarProvider = activity as ActionBarProvider
        return actionBarProvider.actionBar
    }

    fun showError() {
        Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show()
    }

    fun showProgress() {
        progressBar?.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progressBar?.visibility = View.GONE
    }
}