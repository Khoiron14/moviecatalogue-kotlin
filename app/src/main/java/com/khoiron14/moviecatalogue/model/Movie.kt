package com.khoiron14.moviecatalogue.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by khoiron14 on 6/27/2019.
 */
@Parcelize
data class Movie(
    var title: String? = null,
    var rating: String? = null,
    var releaseYear: String? = null,
    var overview: String? = null,
    var poster: Int? = null
) : Parcelable