package com.khoiron14.moviecatalogue.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by khoiron14 on 7/3/2019.
 */
@Parcelize
data class Tvshow(
    var title: String? = null,
    var rating: String? = null,
    var releaseYear: String? = null,
    var overview: String? = null,
    var poster: String? = null
) : Parcelable