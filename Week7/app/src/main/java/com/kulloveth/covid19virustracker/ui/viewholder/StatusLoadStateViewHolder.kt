package com.kulloveth.covid19virustracker.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.kulloveth.covid19virustracker.R
import kotlinx.android.synthetic.main.status_load_state_item.view.*

class StatusLoadStateViewHolder (private val view: View,
                                  retry: () -> Unit
) : RecyclerView.ViewHolder(view) {

    init {
        view.retry_button.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            view.error_msg.text = view.context.getString(R.string.no_internet)
        }
        view.progress_bar.isVisible = loadState is LoadState.Loading
        view.retry_button.isVisible = loadState !is LoadState.Loading
        view.error_msg.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): StatusLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.status_load_state_item, parent, false)
            return StatusLoadStateViewHolder(
                view,
                retry
            )
        }
    }
}