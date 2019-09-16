package com.khoiron14.moviecatalogue.model.favorite

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by khoiron14 on 7/28/2019.
 */
@Entity(tableName = TvShowFavorite.TABLE_NAME)
data class TvShowFavorite(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID) val id: Long?,

    @ColumnInfo(name = COLUMN_TV_SHOW_ID) val tvShowId: Int?,

    @ColumnInfo(name = COLUMN_NAME) val name: String?,

    @ColumnInfo(name = COLUMN_POSTER_PATH) val posterPath: String?
) {
    companion object {
        const val TABLE_NAME = "tvShowFavorite"
        const val COLUMN_ID = BaseColumns._ID
        const val COLUMN_TV_SHOW_ID = "tvShowId"
        const val COLUMN_NAME = "name"
        const val COLUMN_POSTER_PATH = "posterPath"
    }
}