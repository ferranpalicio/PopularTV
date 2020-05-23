package com.pal.playgorund.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pal.populartv.viewmodel.TvShowsViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass


@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(com.pal.populartv.viewmodel.TvShowsViewModel::class)
    abstract fun bindTvShowsViewModel(viewModel: com.pal.populartv.viewmodel.TvShowsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}