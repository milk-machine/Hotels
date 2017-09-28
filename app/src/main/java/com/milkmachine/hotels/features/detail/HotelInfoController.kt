package com.milkmachine.hotels.features.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.milkmachine.hotels.R
import com.milkmachine.hotels.data.dto.HotelDetailedInfo
import com.milkmachine.hotels.features.base.BaseController
import com.milkmachine.hotels.utils.glide.SmartTransformation
import kotlinx.android.synthetic.main.controller_hotel_info.view.*
import toothpick.config.Module
import javax.inject.Inject

class HotelInfoController(args: Bundle) : BaseController(args), HotelInfoView {
    override val layoutId: Int = R.layout.controller_hotel_info
    override val scopeClass: Class<*> = HotelInfoScope::class.java
    override val dependencyModules: Array<Module> = arrayOf(object : Module() {
        init {
            val hotelInfo: HotelDetailedInfo = args.getParcelable(HOTEL_INFO_BUNDLE_KEY)
            bind(HotelDetailedInfo::class.java).toInstance(hotelInfo)
        }
    })

    @[Inject InjectPresenter]
    lateinit var presenter: HotelInfoPresenter

    constructor(hotelInfo: HotelDetailedInfo) : this(Bundle().apply {
        putParcelable(HOTEL_INFO_BUNDLE_KEY, hotelInfo)
    })

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onAttach(view: View) {
        super.onAttach(view)
        setHasOptionsMenu(true)
        getActionBar().setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            if (item.itemId == android.R.id.home)
                router.handleBack()
            else
                super.onOptionsItemSelected(item)

    override fun showHotelInfo(hotelInfo: HotelDetailedInfo) {
        getActionBar().title = hotelInfo.name

        view?.apply {
            setImageWithEdgesTrim(image, hotelInfo.imageUrl)
            rating.rating = hotelInfo.stars
            name.text = hotelInfo.name
            address.text = hotelInfo.address
            distance.text = resources.getString(R.string.hotel_info_distance, hotelInfo.distanceMeters)
            available_suites_count.text = "${hotelInfo.availableSuites.size}"
            available_suites.text = hotelInfo.availableSuites.joinToString(AVAILABLE_SUITES_SEPARATOR)
            coordinates.text = resources.getString(R.string.hotel_info_coordinates,
                    hotelInfo.latitude,
                    hotelInfo.longitude)
        }
    }

    private fun setImageWithEdgesTrim(imageView: ImageView, imageUrl: String) {
        Glide.with(activity)
                .load(imageUrl)
                .apply(RequestOptions()
                        .transform(SmartTransformation(activity!!, EDGE_BORDER_WIDTH_PX)))
                .transition(withCrossFade())
                .into(imageView)
    }

    companion object {
        private const val HOTEL_INFO_BUNDLE_KEY = "HOTEL_INFO_BUNDLE_KEY"
        private const val EDGE_BORDER_WIDTH_PX = 1
        private const val AVAILABLE_SUITES_SEPARATOR = ", "
    }
}