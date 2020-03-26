package com.therafaelreis.mobileui.injection.module

import com.therafaelreis.data.ProjectsDataRepository
import com.therafaelreis.domain.repository.ProjectsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindDataRepository(dataRepository: ProjectsDataRepository): ProjectsRepository
}