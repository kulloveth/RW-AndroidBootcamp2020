package com.kulloveth.covid19virustracker.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.model.Article

/**
 * Paging Adapter for the list of News.
 */
class NewsAdapter:
    PagedListAdapter<Article, NewsViewHolder>(
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
        private val STATUS_DIFF = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean =
                oldItem == newItem
        }
    }

    interface ArticleITemListener {
        fun articleListener(article: Article)
    }
}