package com.therafaelreis.data.repository

import com.therafaelreis.data.model.ProjectEntity
import io.reactivex.Flowable

interface ProjectsRemote {

    fun getProjects(): Flowable<List<ProjectEntity>>

}