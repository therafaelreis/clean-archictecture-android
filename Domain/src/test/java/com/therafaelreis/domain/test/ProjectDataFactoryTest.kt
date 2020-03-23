package com.therafaelreis.domain.test

import com.therafaelreis.domain.model.Project
import java.util.*

object ProjectDataFactoryTest {

    fun randomUUID(): String {
        return UUID.randomUUID().toString()
    }

    fun randomBoolean(): Boolean {
        return Math.random() < 0.5
    }

    fun makeProject(): Project {
        return Project(randomUUID(), randomUUID(), randomUUID(),
            randomUUID(), randomUUID(), randomUUID(),
            randomUUID(), randomBoolean())
    }

    fun makeProjectList(count: Int): List<Project> {
        val projects = mutableListOf<Project>()
        repeat(count) {
            projects.add(makeProject())
        }
        return projects
    }

}