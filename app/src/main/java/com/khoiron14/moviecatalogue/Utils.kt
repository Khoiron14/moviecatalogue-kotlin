package com.khoiron14.moviecatalogue

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.ProgressBar
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

fun connectionAvaiable(activity: Activity): Boolean {
    val manager: ConnectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = manager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}

fun showLoading(state: Boolean, progressBar: ProgressBar) {
    if (state) {
        progressBar.visible()
    } else {
        progressBar.gone()
    }
}