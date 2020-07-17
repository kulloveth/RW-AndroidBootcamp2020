package com.kulloveth.covid19virustracker.ui.status

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.model.CountryStatus

/**
 * Paging Adapter for the list of CountryStatus.
 */
class StatusAdapter(private val listener: StatusITemListener) :
    PagedListAdapter<CountryStatus, StatusViewHolder>(
        STATUS_DIFF
    ) {
    override fun onBindViewHolder(holder: StatusViewHolder, position: Int) {
        getItem(position)?.let { holder.bindStatus(it) }
        holder.view.setOnClickListener {
            getItem(position)?.let { status -> listener.onStatusListener(status) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return StatusViewHolder(
            view
        )
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

    interface StatusITemListener {
        fun onStatusListener(countryStatus: CountryStatus)
    }
}