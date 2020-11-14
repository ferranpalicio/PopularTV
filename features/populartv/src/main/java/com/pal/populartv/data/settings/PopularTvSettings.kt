package com.pal.populartv.data.settings


interface PopularTvSettings {
    companion object {
        const val LAST_TIME_DATA = "last_time_data"
    }

    fun lastTimeDataSaved(): Long

    fun updateLastTimeSaved(timestamp: Long)
}