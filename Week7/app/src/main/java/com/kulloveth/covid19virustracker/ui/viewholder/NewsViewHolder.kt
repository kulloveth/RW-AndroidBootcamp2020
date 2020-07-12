package com.kulloveth.covid19virustracker.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.model.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.headline_item.view.*

class NewsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


    fun bindArticle(article: Article) {
        view.title.text = article.title
        Picasso.get().load(article.urlToImage).error(R.drawable.ic_launcher_background)
            .placeholder(R.drawable.ic_launcher_background).into(view.article_image)
        view.description.text = article.description
    }
}