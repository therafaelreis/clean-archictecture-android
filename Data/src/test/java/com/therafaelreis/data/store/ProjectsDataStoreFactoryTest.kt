package com.therafaelreis.data.store


import com.nhaarman.mockito_kotlin.mock

import org.junit.Assert
import org.junit.Test

class ProjectsDataStoreFactoryTest {

    private val cachedStore = mock<ProjectsCacheDataStore>()
    private val remoteStore = mock<ProjectsRemoteDataStore>()
    private val factory = ProjectDataStoreFactory(cachedStore, remoteStore)

    @Test
    fun getDataStoreReturnsRemoteStoreWhenCachedExpired(){
        Assert.assertEquals(remoteStore, factory.getDataStore(
            projectsCached = true,
            cacheExpired = true
        ))
    }

    @Test
    fun getDataStoreReturnsRemoteStoreWhenProjectsNotCached(){
        Assert.assertEquals(remoteStore, factory.getDataStore(
            projectsCached = false,
            cacheExpired = false
        ))
    }

    @Test
    fun getDataStoreReturnsCachedStore(){
        Assert.assertEquals(cachedStore, factory.getDataStore(
            projectsCached = true,
            cacheExpired = false
        ))
    }

    @Test
    fun getCacheDataSToreReturnsCacheStore(){
        Assert.assertEquals(cachedStore, factory.getCacheDataStore())
    }

}