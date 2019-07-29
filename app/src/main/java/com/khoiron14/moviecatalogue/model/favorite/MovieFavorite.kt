package com.khoiron14.moviecatalogue.model.favorite

/**
 * Created by khoiron14 on 7/28/2019.
 */
data class MovieFavorite(
    val id: Long?,
    val movieId: Int?,
    val movieTitle: String?,
    val moviePosterPath: String
) {
    companion object {
        const val TABLE_MOVIE_FAVORITE: String = "TABLE_MOVIE_FAVORITE"
        const val ID: String = "ID_"
        const val MOVIE_ID: String = "MOVIE_ID"
        const val MOVIE_TITLE: String = "MOVIE_TITLE"
        const val MOVIE_POSTER_PATH: String = "MOVIE_POSTER_PATH"
    }
}