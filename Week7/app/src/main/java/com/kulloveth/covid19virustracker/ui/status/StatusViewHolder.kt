package com.kulloveth.covid19virustracker.ui.status

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.data.db.StatusEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_item.view.*

class StatusViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


    fun bindStatus(statusEntity: StatusEntity) {
        view.country_name.text = statusEntity.country
        view.total_cases_detail.text = statusEntity.total_cases
        view.total_recovered_detail.text = statusEntity.total_recovered
        view.total_deaths_detail.text = statusEntity.total_deaths
        Picasso.get().load(statusEntity.flag).error(R.drawable.ic_launcher_background)
            .placeholder(R.drawable.covid)
            .into(view.flag)
    }
}