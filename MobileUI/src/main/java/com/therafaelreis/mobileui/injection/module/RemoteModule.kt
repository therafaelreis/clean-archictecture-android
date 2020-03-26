package com.therafaelreis.mobileui.injection.module

import com.therafaelreis.data.repository.ProjectsRemote
import com.therafaelreis.mobileui.BuildConfig
import com.therafaelreis.remote.service.GitServiceFactory
import com.therafaelreis.remote.service.GitTrendingService
import com.therafaelreis.remote.service.ProjectsRemoteImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideGithubService(): GitTrendingService {
            return GitServiceFactory.makeGitTrendingService(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindProjectsRemote(projectsRemote: ProjectsRemoteImpl): ProjectsRemote
}