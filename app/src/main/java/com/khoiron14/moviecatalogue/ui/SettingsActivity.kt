package com.khoiron14.moviecatalogue.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import com.khoiron14.moviecatalogue.R
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.toast

class SettingsActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences
    private var stateReleaseReminder: Boolean = false
    private var stateDailyReminder: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

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
                toast(R.string.release_reminder_enable)
            } else {
                toast(R.string.release_reminder_disable)
            }
        }

        sc_daily_reminder.setOnClickListener {
            stateDailyReminder = !stateDailyReminder
            preferences.edit()
                .putBoolean("daily_reminder", stateDailyReminder)
                .apply()
            if (stateDailyReminder) {
                toast(R.string.daily_reminder_enable)
            } else {
                toast(R.string.daily_reminder_disable)
            }
        }

        change_language_setting.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        supportActionBar?.title = resources.getString(R.string.settings)
    }
}
