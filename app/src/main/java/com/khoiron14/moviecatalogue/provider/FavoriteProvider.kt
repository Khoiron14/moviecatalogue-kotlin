package com.khoiron14.moviecatalogue.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.khoiron14.moviecatalogue.database.AppDatabase
import com.khoiron14.moviecatalogue.model.favorite.MovieFavorite
import com.khoiron14.moviecatalogue.model.favorite.TvShowFavorite

/**
 * Created by khoiron14 on 9/3/2019.
 */
class FavoriteProvider : ContentProvider() {

    companion object {
        private const val AUTHORITIES = "com.khoiron14.moviecatalogue"
        private const val MOVIE = 101
        private const val TV_SHOW = 201
    }

    private val uriMatcherMovie = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITIES, MovieFavorite.TABLE_NAME, MOVIE)
    }

    private val uriMatcherTvShow = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITIES, TvShowFavorite.TABLE_NAME, TV_SHOW)
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? = null

    override fun query(
        p0: Uri,
        p1: Array<String>?,
        p2: String?,
        p3: Array<String>?,
        p4: String?
    ): Cursor? {
        val movieFavoriteDao = AppDatabase.getInstance(context)?.movieFavoriteDao()
        val tvShowFavoriteDao = AppDatabase.getInstance(context)?.tvShowFavoriteDao()

        return when {
            uriMatcherMovie.match(p0) == MOVIE -> movieFavoriteDao?.getMovieFavoritesCursor()
            uriMatcherTvShow.match(p0) == TV_SHOW -> tvShowFavoriteDao?.getTvShowFavoritesCursor()
            else -> throw IllegalArgumentException("Unknown URI")
        }

    }

    override fun onCreate(): Boolean = true

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<String>?): Int = 0

    override fun delete(p0: Uri, p1: String?, p2: Array<String>?): Int = 0

    override fun getType(p0: Uri): String? = null
}