package com.khoiron14.moviecatalogue.model.favorite

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by khoiron14 on 7/28/2019.
 */
@Entity(tableName = MovieFavorite.TABLE_NAME)
data class MovieFavorite(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID) val id: Long?,

    @ColumnInfo(name = COLUMN_MOVIE_ID) val movieId: Int?,

    @ColumnInfo(name = COLUMN_TITLE) val title: String?,

    @ColumnInfo(name = COLUMN_POSTER_PATH) val posterPath: String?
) {
    companion object {
        const val TABLE_NAME = "movieFavorite"
        const val COLUMN_ID = BaseColumns._ID
        const val COLUMN_MOVIE_ID = "movieId"
        const val COLUMN_TITLE = "title"
        const val COLUMN_POSTER_PATH = "posterPath"
    }
}