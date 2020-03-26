package com.therafaelreis.mobileui.injection

import android.app.Application
import com.therafaelreis.mobileui.GithubTrendingApplication

import com.therafaelreis.mobileui.injection.module.ApplicationModule
import com.therafaelreis.mobileui.injection.module.CacheModule
import com.therafaelreis.mobileui.injection.module.DataModule
import com.therafaelreis.mobileui.injection.module.PresentationModule
import com.therafaelreis.mobileui.injection.module.RemoteModule
import com.therafaelreis.mobileui.injection.module.UiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,
    ApplicationModule::class,
    UiModule::class,
    PresentationModule::class,
    DataModule::class,
    CacheModule::class,
    RemoteModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: GithubTrendingApplication)

}