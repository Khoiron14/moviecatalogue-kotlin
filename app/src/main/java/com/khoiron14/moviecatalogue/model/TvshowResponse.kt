package com.khoiron14.moviecatalogue.model

/**
 * Created by khoiron14 on 7/22/2019.
 */
data class TvshowResponse(
    val page: Int? = null,
    val total_results: Int? = null,
    val total_pages: Int? = null,
    val results: List<Tvshow>? = null
)