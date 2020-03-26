package com.therafaelreis.cache.test.factory

import com.therafaelreis.cache.model.CachedProject
import com.therafaelreis.data.model.ProjectEntity

object ProjectDataFactory {

    fun makeCachedProject(): CachedProject {
        return CachedProject(DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            false)
    }

    fun makeBookmarkedCachedProject(): CachedProject {
        return CachedProject(DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            true)
    }

    fun makeProjectEntity(): ProjectEntity {
        return ProjectEntity(DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomBoolean())
    }

    fun makeBookmarkedProjectEntity(): ProjectEntity {
        return ProjectEntity(DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            true)
    }

    fun makeNonBookmarkedProjectEntity(): ProjectEntity {
        return ProjectEntity("1",
            "hello", "hello there",
            "4", "03/29/2010",
            "somebody", "somebody",
            true)
    }
}