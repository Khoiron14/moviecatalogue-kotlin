package com.khoiron14.moviecatalogue

import android.view.View
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by khoiron14 on 7/20/2019.
 */
fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun convertDate(date: String?): String? {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    format.timeZone = TimeZone.getTimeZone("UTC")

    val formatDate = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    val dateConvert = format.parse(date)
    return formatDate.format(dateConvert)
}