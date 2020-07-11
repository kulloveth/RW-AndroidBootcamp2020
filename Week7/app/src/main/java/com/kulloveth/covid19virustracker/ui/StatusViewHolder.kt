package com.kulloveth.covid19virustracker.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kulloveth.covid19virustracker.model.CountryStatus
import kotlinx.android.synthetic.main.rv_item.view.*

class StatusViewHolder(val view:View):RecyclerView.ViewHolder(view) {

    fun bindStatus(countryStatus: CountryStatus){
        view.country_name.text = countryStatus.country
        view.confirmed_detail.text = countryStatus.total_cases
        view.recovered_detail.text = countryStatus.total_recovered
        view.deaths_detail.text = countryStatus.total_deaths
    }
}