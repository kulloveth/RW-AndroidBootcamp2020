package com.kulloveth.covid19virustracker.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.data.db.NewsEntity

/**
 * Paging Adapter for smooth scrolling of news items.
 */
class NewsAdapter :
    PagedListAdapter<NewsEntity, NewsViewHolder>(
        STATUS_DIFF
    ) {
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bindArticle(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.headline_item, parent, false)
        return NewsViewHolder(
            view
        )
    }

    companion object {
        private val STATUS_DIFF = object : DiffUtil.ItemCallback<NewsEntity>() {
            override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: NewsEntity,
                newItem: NewsEntity
            ): Boolean =
                oldItem == newItem
        }
    }

    interface ArticleITemListener {
        fun articleListener(newsEntity: NewsEntity)
    }
}