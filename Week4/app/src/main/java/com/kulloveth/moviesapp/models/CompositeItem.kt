package com.kulloveth.moviesapp.models

class CompositeItem {
    lateinit var movie: Movie
        private set

    lateinit var header: Header
        private set

    var isHeader = false
        private set

    companion object {
        fun withMovie(movie: Movie): CompositeItem {
            val compositeItem = CompositeItem()
            compositeItem.movie = movie
            return compositeItem
        }


        fun withHeader(header: Header): CompositeItem {
            val compositeItem = CompositeItem()
            compositeItem.header = header
            compositeItem.isHeader = true
            return compositeItem
        }
    }

    override fun toString(): String {
        return if (isHeader) header.name else movie.title
    }
}

