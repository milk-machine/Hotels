package com.milkmachine.hotels.features.list

import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.milkmachine.hotels.R
import com.milkmachine.hotels.data.dto.HotelDetailedInfo
import com.milkmachine.hotels.data.dto.HotelShortInfo
import com.milkmachine.hotels.features.base.BaseController
import com.milkmachine.hotels.features.detail.HotelInfoController
import kotlinx.android.synthetic.main.controller_hotels_list.view.*
import javax.inject.Inject

class HotelsListController : BaseController(), HotelsListView {
    override val scopeClass: Class<*> = HotelsListScope::class.java
    override val layoutId: Int = R.layout.controller_hotels_list

    @[Inject InjectPresenter]
    lateinit var presenter: HotelsListPresenter

    private val hotelsAdapter: HotelsListAdapter = HotelsListAdapter { presenter.onHotelItemClicked(it) }

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View =
            super.onCreateView(inflater, container).apply {
                recycler_view.layoutManager = LinearLayoutManager(context)
                recycler_view.adapter = hotelsAdapter

                val itemDivider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                itemDivider.setDrawable(ContextCompat.getDrawable(context, R.drawable.divider_horizontal))
                recycler_view.addItemDecoration(itemDivider)
            }

    override fun onAttach(view: View) {
        super.onAttach(view)
        setHasOptionsMenu(true)
        getActionBar().setDisplayHomeAsUpEnabled(false)
        getActionBar().setTitle(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_hotels_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.isChecked = true

        when (item.itemId) {
            R.id.by_distance -> presenter.onSortByDistancePicked()
            R.id.by_available_suites -> presenter.onSortByAvailableSuitesPicked()
        }

        return true
    }

    override fun showHotelsList(hotelsList: List<HotelShortInfo>) {
        hotelsAdapter.hotels = hotelsList
    }

    override fun navigateToDetailInfoScreen(hotelInfo: HotelDetailedInfo) {
        router.pushController(RouterTransaction.with(HotelInfoController(hotelInfo))
                .pushChangeHandler(FadeChangeHandler())
                .popChangeHandler(FadeChangeHandler()))
    }
}