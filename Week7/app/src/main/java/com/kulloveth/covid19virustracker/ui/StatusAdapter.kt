package com.kulloveth.covid19virustracker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.model.CountryStatus

/**
 * Adapter for the list of CountryStatus.
 */
class StatusAdapter : PagingDataAdapter<CountryStatus, StatusViewHolder>(STATUS_DIFF) {
    override fun onBindViewHolder(holder: StatusViewHolder, position: Int) {
        getItem(position)?.let { holder.bindStatus(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return StatusViewHolder(view)
    }

    companion object {
        private val STATUS_DIFF = object : DiffUtil.ItemCallback<CountryStatus>() {
            override fun areItemsTheSame(oldItem: CountryStatus, newItem: CountryStatus): Boolean =
                oldItem.country == newItem.country

            override fun areContentsTheSame(
                oldItem: CountryStatus,
                newItem: CountryStatus
            ): Boolean =
                oldItem == newItem
        }
    }
}