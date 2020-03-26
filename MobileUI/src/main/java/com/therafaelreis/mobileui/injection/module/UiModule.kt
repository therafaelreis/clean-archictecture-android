package com.therafaelreis.mobileui.injection.module

import com.therafaelreis.domain.executor.PostExecutionThread
import com.therafaelreis.mobileui.UiThread
import com.therafaelreis.mobileui.browse.BrowseActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesBrowseActivity(): BrowseActivity
}