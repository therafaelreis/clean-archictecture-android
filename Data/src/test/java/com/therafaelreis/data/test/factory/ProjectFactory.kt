package com.therafaelreis.data.test.factory

import com.therafaelreis.data.model.ProjectEntity
import com.therafaelreis.domain.model.Project

object ProjectFactory {

    fun makeProjectEntity(): ProjectEntity {
        return ProjectEntity(
            DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomBoolean()
        )
    }

    fun makeProject(): Project {
        return Project(
            DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomBoolean()
        )
    }
}