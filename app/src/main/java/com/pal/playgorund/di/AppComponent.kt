package com.pal.playgorund.di

import android.content.Context
import com.pal.core.di.CoreComponent
import com.pal.core.di.scope.AppScope
//import com.pal.core.di.CoreComponent
import com.pal.playgorund.ui.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [ContextModule::class, ViewModelModule::class],
    dependencies = [CoreComponent::class]
)
@AppScope
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context,
            coreComponent: CoreComponent
        ): AppComponent
    }

    fun inject(activity: MainActivity)
}