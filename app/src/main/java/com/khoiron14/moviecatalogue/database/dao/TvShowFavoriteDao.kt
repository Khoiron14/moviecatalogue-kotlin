package com.khoiron14.moviecatalogue.database.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.khoiron14.moviecatalogue.model.favorite.TvShowFavorite
import com.khoiron14.moviecatalogue.model.favorite.TvShowFavorite.Companion.COLUMN_ID
import com.khoiron14.moviecatalogue.model.favorite.TvShowFavorite.Companion.COLUMN_TV_SHOW_ID
import com.khoiron14.moviecatalogue.model.favorite.TvShowFavorite.Companion.TABLE_NAME

/**
 * Created by khoiron14 on 9/4/2019.
 */
@Dao
interface TvShowFavoriteDao {

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_ID DESC")
    suspend fun getTvShowFavorites(): List<TvShowFavorite>?

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_TV_SHOW_ID IN(:tvShowId) LIMIT 1")
    suspend fun getTvShowFavorite(tvShowId: Int?): TvShowFavorite?

    @Insert
    suspend fun insertTvShowFavorite(tvShowFavorite: TvShowFavorite?)

    @Query("DELETE FROM $TABLE_NAME WHERE $COLUMN_TV_SHOW_ID IN(:tvShowId)")
    suspend fun deleteTvShowFavorite(tvShowId: Int?)

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_ID DESC")
    fun getTvShowFavoritesCursor(): Cursor?
}