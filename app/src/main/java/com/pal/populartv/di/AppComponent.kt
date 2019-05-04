package com.pal.populartv.di

import com.pal.populartv.ui.MainActivity
import dagger.Component

@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}