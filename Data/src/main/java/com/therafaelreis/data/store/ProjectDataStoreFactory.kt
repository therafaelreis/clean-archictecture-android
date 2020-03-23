package com.therafaelreis.data.store

import com.therafaelreis.data.repository.ProjectsDataStore
import javax.inject.Inject

class ProjectDataStoreFactory @Inject constructor(private val projectsCacheDataStore: ProjectsCacheDataStore,
                                                  private val projectsRemoteDataStore: ProjectsRemoteDataStore){

    open fun getDataStore(projectsCached: Boolean,
                          cacheExpired: Boolean): ProjectsDataStore{
        return if(projectsCached && !cacheExpired){
            projectsCacheDataStore
        }else{
            projectsRemoteDataStore
        }
    }

    open fun getCacheDataStore(): ProjectsDataStore{
        return projectsCacheDataStore
    }

}