package com.pal.core.domain


interface AppSettings {
    companion object {
        const val PREF_NAME = "app_settings"
        const val LAST_TIME_DATA = "last_time_data"
    }

    fun lastTimeDataSaved(): Long

    fun updateLastTimeSaved(timestamp: Long)
}