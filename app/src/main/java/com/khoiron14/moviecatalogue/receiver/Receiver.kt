package com.khoiron14.moviecatalogue.receiver

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.ui.MainActivity
import java.util.*

/**
 * Created by khoiron14 on 8/22/2019.
 */
interface Receiver {

    var idReminder: Int
    var reminderHour: Int

    private fun getReminderTime(): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        if (calendar.get(Calendar.HOUR_OF_DAY) > reminderHour) {
            calendar.add(Calendar.DATE, 1)
        }

        calendar.set(Calendar.HOUR_OF_DAY, reminderHour)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        return calendar
    }

    fun setAlarm(context: Context, message: String) {
        val intent = Intent(context, this.javaClass)
        val pendingIntent = PendingIntent.getBroadcast(context, idReminder, intent, 0)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            getReminderTime().timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context, message: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, this.javaClass)
        val pendingIntent = PendingIntent.getBroadcast(context, idReminder, intent, 0)

        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showNotification(context: Context, title: String, message: String) {
        val channelId = "Channel_1"
        val channelName = "AlarmManager channel"

        val resultIntent = Intent(context, MainActivity::class.java)
        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addNextIntentWithParentStack(resultIntent)
        val resultPendingIntent =
            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_movie_black_24dp)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
            .setContentIntent(resultPendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(System.currentTimeMillis().toInt(), notification)
    }
}