package com.therafaelreis.mobileui.browse


import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.therafaelreis.mobileui.R
import com.therafaelreis.mobileui.injection.ViewModelFactory
import com.therafaelreis.mobileui.mapper.ProjectViewMapper
import com.therafaelreis.mobileui.model.Project

import com.therafaelreis.presentation.BrowseProjectsViewModel
import com.therafaelreis.presentation.model.ProjectView
import com.therafaelreis.presentation.state.Resource
import com.therafaelreis.presentation.state.ResourceState
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_browse.*
import javax.inject.Inject


class BrowseActivity : AppCompatActivity() {

    @Inject lateinit var browseAdapter: BrowseAdapter
    @Inject lateinit var mapper: ProjectViewMapper
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var browseViewModel: BrowseProjectsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        AndroidInjection.inject(this)

        browseViewModel = ViewModelProvider(this, viewModelFactory)
                .get(BrowseProjectsViewModel::class.java)

        setupBrowseRecycler()
    }

    override fun onStart() {
        super.onStart()
        browseViewModel.getProjects().observe(this,
                Observer<Resource<List<ProjectView>>> {
                    it?.let {
                        handleDataState(it)
                    }
                })
        browseViewModel.fetchProjects()
    }


    private fun setupBrowseRecycler() {
        browseAdapter.projectListener = projectListener
        rv_projects.apply {
            layoutManager = LinearLayoutManager(this@BrowseActivity)
            adapter = browseAdapter
        }
    }

    private fun handleDataState(resource: Resource<List<ProjectView>>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                setupScreenForSuccess(resource.data?.map {
                    mapper.mapToView(it)
                })
            }
            ResourceState.LOADING -> {
                progress.visibility = View.VISIBLE
                rv_projects.visibility = View.GONE
            }
        }
    }

    private fun setupScreenForSuccess(projects: List<Project>?) {
        progress.visibility = View.GONE
        projects?.let {
            browseAdapter.projects = it
            browseAdapter.notifyDataSetChanged()
            rv_projects.visibility = View.VISIBLE
        } ?: run {

        }
    }

    private val projectListener = object : ProjectListener {
        override fun onBookmarkedProjectClicked(projectId: String) {
            browseViewModel.unbookmarkProject(projectId)
        }

        override fun onProjectClicked(projectId: String) {
            browseViewModel.bookmarkProject(projectId)
        }
    }

}