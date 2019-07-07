package com.pal.populartv.di

import android.content.Context
import com.pal.populartv.ui.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class, ViewModelModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance fun applicationContext(applicationContext: Context): Builder
        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
}