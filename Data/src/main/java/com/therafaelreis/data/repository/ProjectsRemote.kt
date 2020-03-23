package com.therafaelreis.data.repository

import com.therafaelreis.data.model.ProjectEntity
import io.reactivex.Observable

interface ProjectsRemote {

    fun getProjects(): Observable<List<ProjectEntity>>

}