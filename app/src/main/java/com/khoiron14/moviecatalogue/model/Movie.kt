package com.khoiron14.moviecatalogue.model

import com.squareup.moshi.Json

/**
 * Created by khoiron14 on 7/3/2019.
 */
data class Movie(
    val id: Int? = null,
    val title: String? = null,
    @field:Json(name = "vote_average") val rating: String? = null,
    @field:Json(name = "release_date") var releaseDate: String? = null,
    val overview: String? = null,
    @field:Json(name = "poster_path") val posterPath: String? = null,
    @field:Json(name = "backdrop_path") val backdropPath: String? = null
)