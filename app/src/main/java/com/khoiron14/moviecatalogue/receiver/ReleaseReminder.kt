package com.khoiron14.moviecatalogue.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.connectionAvailable
import com.khoiron14.moviecatalogue.currentLocale
import com.khoiron14.moviecatalogue.getDate
import com.khoiron14.moviecatalogue.service.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 * Created by khoiron14 on 8/22/2019.
 */
class ReleaseReminder : BroadcastReceiver(), Receiver {

    override var idReminder: Int = 101
    override var reminderHour: Int = 8

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (!connectionAvailable(p0!!)) return

        val service = RetrofitFactory.service()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getMovieList(currentLocale.toLanguageTag(), getDate(), getDate())
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.results?.let {
                            for (movie in it) {
                                showNotification(
                                    p0,
                                    movie.title!!,
                                    movie.title + " " + p0.resources.getString(R.string.msg_release_reminder)
                                )
                            }
                        }
                    } else {
                        error("Error ${response.code()}")
                    }
                } catch (e: HttpException) {
                    error("Exception ${e.message()}")
                } catch (e: Throwable) {
                    error("Oops something went wrong")
                }
            }
        }
    }
}