package com.therafaelreis.mobileui.injection.module


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.therafaelreis.mobileui.injection.ViewModelFactory
import com.therafaelreis.presentation.BrowseBookmarkedProjectsViewModel
import com.therafaelreis.presentation.BrowseProjectsViewModel

import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class PresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(BrowseProjectsViewModel::class)
    abstract fun bindBrowseProjectsViewModel(viewModel: BrowseProjectsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BrowseBookmarkedProjectsViewModel::class)
    abstract fun bindBrowseBookmarkedProjectsViewModel(
            viewModel: BrowseBookmarkedProjectsViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)