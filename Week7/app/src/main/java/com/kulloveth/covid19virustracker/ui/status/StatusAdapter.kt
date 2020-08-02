package com.kulloveth.covid19virustracker.ui.status

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.data.db.StatusEntity

/**
 * Paging Adapter for Smooth scrolling of status items.
 */
class StatusAdapter(private val listener: StatusITemListener) :
    ListAdapter<StatusEntity, StatusViewHolder>(
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
        private val STATUS_DIFF = object : DiffUtil.ItemCallback<StatusEntity>() {
            override fun areItemsTheSame(oldItem: StatusEntity, newItem: StatusEntity): Boolean =
                oldItem.country == newItem.country

            override fun areContentsTheSame(
                oldItem: StatusEntity,
                newItem: StatusEntity
            ): Boolean =
                oldItem == newItem
        }
    }

    interface StatusITemListener {
        fun onStatusListener(statusEntity: StatusEntity)
    }
}