package com.pal.playgorund

import android.app.Application
import com.pal.core.di.DaggerCoreComponent
import com.pal.populartv.di.DaggerPopularTVComponent
import com.pal.populartv.di.PopularTVComponent
import com.pal.populartv.di.PopularTvInjectorProvider
import com.playground.database.di.DaggerDatabaseComponent

class PlaygroundApp : Application(), PopularTvInjectorProvider {

    //in order to expose core component for other modules
    private val coreComponent by lazy { DaggerCoreComponent.factory().create(this) }
    private val databaseComponent by lazy { DaggerDatabaseComponent.factory().create(this) }

    override fun popularTvInjector(): PopularTVComponent {
        return DaggerPopularTVComponent.factory().create(coreComponent, databaseComponent)
    }
}