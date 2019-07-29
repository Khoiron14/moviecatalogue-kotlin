package com.khoiron14.moviecatalogue.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.khoiron14.moviecatalogue.model.favorite.MovieFavorite
import com.khoiron14.moviecatalogue.model.favorite.TvshowFavorite
import org.jetbrains.anko.db.*

/**
 * Created by khoiron14 on 7/28/2019.
 */
class MyDatabaseOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "Favorite.db", null, 1) {

    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(context.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.createTable(
            MovieFavorite.TABLE_MOVIE_FAVORITE, true,
            MovieFavorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            MovieFavorite.MOVIE_ID to INTEGER + UNIQUE,
            MovieFavorite.MOVIE_TITLE to TEXT,
            MovieFavorite.MOVIE_POSTER_PATH to TEXT
        )

        p0?.createTable(
            TvshowFavorite.TABLE_TVSHOW_FAVORITE, true,
            TvshowFavorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            TvshowFavorite.TVSHOW_ID to INTEGER + UNIQUE,
            TvshowFavorite.TVSHOW_NAME to TEXT,
            TvshowFavorite.TVSHOW_POSTER_PATH to TEXT
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.dropTable(MovieFavorite.TABLE_MOVIE_FAVORITE, true)
        p0?.dropTable(TvshowFavorite.TABLE_TVSHOW_FAVORITE, true)
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)