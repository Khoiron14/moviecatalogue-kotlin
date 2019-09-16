package com.khoiron14.moviecatalogue.database.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.khoiron14.moviecatalogue.model.favorite.MovieFavorite
import com.khoiron14.moviecatalogue.model.favorite.MovieFavorite.Companion.COLUMN_ID
import com.khoiron14.moviecatalogue.model.favorite.MovieFavorite.Companion.COLUMN_MOVIE_ID
import com.khoiron14.moviecatalogue.model.favorite.MovieFavorite.Companion.TABLE_NAME

/**
 * Created by khoiron14 on 9/4/2019.
 */
@Dao
interface MovieFavoriteDao {

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_ID DESC")
    suspend fun getMovieFavorites(): List<MovieFavorite>?

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_MOVIE_ID IN(:movieId) LIMIT 1")
    suspend fun getMovieFavorite(movieId: Int?): MovieFavorite?

    @Insert
    suspend fun insertMovieFavorite(movieFavorite: MovieFavorite?)

    @Query("DELETE FROM $TABLE_NAME WHERE $COLUMN_MOVIE_ID IN(:movieId)")
    suspend fun deleteMovieFavorite(movieId: Int?)

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_ID DESC")
    fun getMovieFavoritesCursor(): Cursor?
}