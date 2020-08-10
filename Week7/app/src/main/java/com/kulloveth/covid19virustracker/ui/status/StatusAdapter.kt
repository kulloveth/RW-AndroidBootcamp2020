package com.kulloveth.covid19virustracker.ui.status

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.data.db.StatusEntity
import kotlinx.android.synthetic.main.rv_item.view.*

/**
 * Paging Adapter for Smooth scrolling of status items.
 */
class StatusAdapter(private val listener: StatusITemListener) :
    ListAdapter<StatusEntity, StatusViewHolder>(
        STATUS_DIFF
    ) {
    private var lastPosition = -1
    override fun onBindViewHolder(holder: StatusViewHolder, position: Int) {
        getItem(position)?.let { holder.bindStatus(it) }
        holder.view.setOnClickListener {
            getItem(position)?.let { status -> listener.onStatusListener(status) }
        }
        setAnimation(holder.view.cv_item,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return StatusViewHolder(
            view
        )
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(viewToAnimate.context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
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