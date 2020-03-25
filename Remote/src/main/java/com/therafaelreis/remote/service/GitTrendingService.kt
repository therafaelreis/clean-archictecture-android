package com.therafaelreis.remote.service

import com.therafaelreis.remote.model.ProjectsResponseModel
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface GitTrendingService {

    @GET("search/repositories")
    fun searchRepositories(@Query("q") query: String,
                           @Query("sort") sortBy: String,
                           @Query("order") order: String) : Flowable<ProjectsResponseModel>

}