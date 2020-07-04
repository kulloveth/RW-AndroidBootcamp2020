package com.kulloveth.moviesapp.models



class CompositeItem {
     var movie: Movie? = null
        private set

     var header: Header? = null
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
        return (if (isHeader) header?.name else movie?.title) as String
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CompositeItem) return false

        if (movie != other.movie) return false
        if (header != other.header) return false
        if (isHeader != other.isHeader) return false

        return true
    }

    override fun hashCode(): Int {
        var result = movie?.hashCode() ?: 0
        result = 31 * result + (header?.hashCode() ?: 0)
        result = 31 * result + isHeader.hashCode()
        return result
    }


}

