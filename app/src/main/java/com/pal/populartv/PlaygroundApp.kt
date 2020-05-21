package com.pal.populartv

import android.app.Application
import android.content.Context
import com.pal.core.di.CoreComponent
import com.pal.core.di.DaggerCoreComponent
import com.pal.populartv.di.AppComponent
import com.pal.populartv.di.DaggerAppComponent

class PlaygroundApp : Application() {

    //in order to expose core component for other modules
    lateinit var coreComponent: CoreComponent
    companion object {
        @JvmStatic
        fun coreComponent(context: Context) =
            (context.applicationContext as PlaygroundApp).coreComponent
    }

    val appComponent : AppComponent by lazy {
        DaggerAppComponent.factory().create(this, coreComponent)
    }

    override fun onCreate() {
        super.onCreate()
        coreComponent = DaggerCoreComponent.factory().create()
    }
}