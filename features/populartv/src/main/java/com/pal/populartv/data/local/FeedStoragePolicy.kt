package com.pal.populartv.data.local

import com.pal.core.data_contract.StoragePolicy
import com.pal.populartv.data.settings.PopularTvSettings
import javax.inject.Inject

class FeedStoragePolicy @Inject constructor(
    private val popularTvSettings: PopularTvSettings
) : StoragePolicy {

    override fun isInvalidData() =
        System.currentTimeMillis() - popularTvSettings.lastTimeDataSaved() > ONE_MIN

    companion object {
        const val ONE_MIN = 1000 * 60
    }
}