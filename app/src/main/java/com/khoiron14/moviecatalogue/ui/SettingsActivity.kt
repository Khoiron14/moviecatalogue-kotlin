package com.khoiron14.moviecatalogue.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.receiver.DailyReminder
import com.khoiron14.moviecatalogue.receiver.ReleaseReminder
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences
    private lateinit var dailyReminder: DailyReminder
    private lateinit var releaseReminder: ReleaseReminder
    private var stateReleaseReminder: Boolean = false
    private var stateDailyReminder: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        dailyReminder = DailyReminder()
        releaseReminder = ReleaseReminder()

        preferences = getSharedPreferences("PREFS", 0)
        stateReleaseReminder = preferences.getBoolean("release_reminder", false)
        stateDailyReminder = preferences.getBoolean("daily_reminder", false)

        sc_release_reminder.isChecked = stateReleaseReminder
        sc_daily_reminder.isChecked = stateDailyReminder

        sc_release_reminder.setOnClickListener {
            stateReleaseReminder = !stateReleaseReminder
            preferences.edit()
                .putBoolean("release_reminder", stateReleaseReminder)
                .apply()
            if (stateReleaseReminder) {
                releaseReminder.setAlarm(
                    this,
                    resources.getString(R.string.release_reminder_enable)
                )
            } else {
                releaseReminder.cancelAlarm(
                    this,
                    resources.getString(R.string.release_reminder_disable)
                )
            }
        }

        sc_daily_reminder.setOnClickListener {
            stateDailyReminder = !stateDailyReminder
            preferences.edit()
                .putBoolean("daily_reminder", stateDailyReminder)
                .apply()
            if (stateDailyReminder) {
                dailyReminder.setAlarm(this, resources.getString(R.string.daily_reminder_enable))
            } else {
                dailyReminder.cancelAlarm(
                    this,
                    resources.getString(R.string.daily_reminder_disable)
                )
            }
        }

        change_language_setting.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        supportActionBar?.title = resources.getString(R.string.settings)
    }
}
