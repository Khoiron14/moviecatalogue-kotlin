package com.khoiron14.moviecatalogue.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.khoiron14.moviecatalogue.R


/**
 * Created by khoiron14 on 8/21/2019.
 */
class DailyReminder : BroadcastReceiver(), Receiver {

    override var idReminder: Int = 100
    override var reminderHour: Int = 7

    override fun onReceive(p0: Context?, p1: Intent?) {
        showNotification(
            p0!!,
            p0.resources.getString(R.string.daily_reminder),
            p0.resources.getString(R.string.msg_daily_reminder)
        )
    }
}