package com.milkmachine.hotels.features.list

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.milkmachine.hotels.R
import com.milkmachine.hotels.data.dto.HotelShortInfo
import kotlinx.android.synthetic.main.item_list_hotel.view.*

class HotelsListAdapter(private val itemClickListener: (HotelShortInfo) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var hotels: List<HotelShortInfo> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = hotels.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_hotel, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val hotel = hotels[position]

        holder.itemView.apply {
            setupNameAndDistanceText(name_and_distance, hotel)
            stars.rating = hotel.stars
            available_suites.text =
                    resources.getQuantityString(R.plurals.hotels_list_distance_available_suites,
                            hotel.availableSuites.size,
                            hotel.availableSuites.size)
            setOnClickListener { itemClickListener(hotel) }
        }
    }

    private fun setupNameAndDistanceText(textView: TextView, hotel: HotelShortInfo) {
        val text = textView.resources.getString(R.string.hotels_list_name_with_distance_meters,
                hotel.name, hotel.distanceMeters)
        val indexOfDistance = text.lastIndexOf('(')
        val colorForDistance = ContextCompat.getColor(textView.context, R.color.secondary_text)

        val spannableText = SpannableString(text).apply {
            setSpan(RelativeSizeSpan(0.9f), indexOfDistance, text.length, 0)
            setSpan(ForegroundColorSpan(colorForDistance), indexOfDistance, text.length, 0)
        }

        textView.text = spannableText
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}