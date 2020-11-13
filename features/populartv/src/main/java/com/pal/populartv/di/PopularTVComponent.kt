package com.pal.populartv.di

import androidx.lifecycle.ViewModelProvider
import com.pal.core.di.CoreComponent
import com.pal.core.di.scope.FeatureScope
import com.playground.database.di.DatabaseComponent
import dagger.Component

@Component(
    modules = [ViewModelModule::class, PopularTvModule::class],
    dependencies = [CoreComponent::class, DatabaseComponent::class]
)

@FeatureScope
interface PopularTVComponent {

    val viewModelFactory: ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent, databaseComponent: DatabaseComponent): PopularTVComponent
    }
}