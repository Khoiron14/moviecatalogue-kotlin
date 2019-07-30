package com.khoiron14.moviecatalogue.model.tvshow

/**
 * Created by khoiron14 on 7/22/2019.
 */
data class TvShowResponse(
    val page: Int? = null,
    val total_results: Int? = null,
    val total_pages: Int? = null,
    val results: List<TvShow>? = null
)