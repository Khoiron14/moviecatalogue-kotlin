package com.khoiron14.moviecatalogue.model.favorite

/**
 * Created by khoiron14 on 7/28/2019.
 */
data class TvshowFavorite(
    val id: Long?,
    val tvshowId: Int?,
    val tvshowName: String?,
    val tvshowPosterPath: String?
) {
    companion object {
        const val TABLE_TVSHOW_FAVORITE: String = "TABLE_TVSHOW_FAVORITE"
        const val ID: String = "ID_"
        const val TVSHOW_ID: String = "TVSHOW_ID"
        const val TVSHOW_NAME: String = "TVSHOW_NAME"
        const val TVSHOW_POSTER_PATH: String = "TVSHOW_POSTER_PATH"
    }
}