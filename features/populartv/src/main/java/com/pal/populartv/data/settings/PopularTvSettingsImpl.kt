package com.pal.populartv.data.settings

import android.content.SharedPreferences
import javax.inject.Inject


class PopularTvSettingsImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : PopularTvSettings {

    override fun updateLastTimeSaved(timestamp: Long) {
        sharedPreferences.edit().putLong(PopularTvSettings.LAST_TIME_DATA, timestamp).apply()
    }

    override fun lastTimeDataSaved(): Long {
        return sharedPreferences.getLong(PopularTvSettings.LAST_TIME_DATA, 0L)
    }
}