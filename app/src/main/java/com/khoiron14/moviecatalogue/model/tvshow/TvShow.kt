package com.khoiron14.moviecatalogue.model.tvshow

import com.squareup.moshi.Json

/**
 * Created by khoiron14 on 7/3/2019.
 */
data class TvShow(
    val id: Int? = null,
    @field:Json(name = "original_name") val name: String? = null,
    @field:Json(name = "vote_average") val rating: String? = null,
    @field:Json(name = "first_air_date") var firstAirDate: String? = null,
    val overview: String? = null,
    @field:Json(name = "poster_path") val posterPath: String? = null,
    @field:Json(name = "backdrop_path") val backdropPath: String? = null
)