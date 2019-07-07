package com.pal.populartv.data.local

import android.content.SharedPreferences
import com.pal.populartv.domain.AppSettings
import javax.inject.Inject


class AppSettingsImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : AppSettings {

    override fun updatLastTimeSaved(timestamp: Long) {
        sharedPreferences.edit().putLong(AppSettings.LAST_TIME_DATA, timestamp).apply()
    }

    override fun lastTimeDataSaved(): Long {
        return sharedPreferences.getLong(AppSettings.LAST_TIME_DATA, 0L)
    }
}