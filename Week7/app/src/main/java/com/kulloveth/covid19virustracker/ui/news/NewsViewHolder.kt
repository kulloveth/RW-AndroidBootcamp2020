package com.kulloveth.covid19virustracker.ui.news

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.model.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.headline_item.view.*


class NewsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


    fun bindArticle(article: Article) {
        view.title.text = article.title
        var path: String? = article.urlToImage
        if (path != null) {
            if (path.isEmpty())
                path = null
            Picasso.get().load(path).error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.covid).into(view.article_image)
        } else {
            view.article_image.setImageResource(R.drawable.covid)
        }
        view.description.text = article.description
    }
}