package com.pal.populartv.di

import android.content.Context
import com.pal.populartv.ui.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class, ViewModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context
        ) : AppComponent
    }

    fun inject(activity: MainActivity)
}