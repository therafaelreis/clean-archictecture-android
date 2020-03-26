package com.therafaelreis.mobileui.injection.module

import android.app.Application
import com.therafaelreis.cache.ProjectsCacheImpl
import com.therafaelreis.cache.db.ProjectsDatabase
import com.therafaelreis.data.repository.ProjectsCache
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CacheModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesDataBase(application: Application): ProjectsDatabase {
            return ProjectsDatabase.getInstance(application)
        }
    }

    @Binds
    abstract fun bindProjectsCache(projectsCache: ProjectsCacheImpl): ProjectsCache
}