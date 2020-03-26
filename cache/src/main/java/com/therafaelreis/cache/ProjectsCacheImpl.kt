package com.therafaelreis.cache

import com.therafaelreis.cache.db.ProjectsDatabase
import com.therafaelreis.cache.mapper.CachedProjectMapper
import com.therafaelreis.cache.model.Config
import com.therafaelreis.data.model.ProjectEntity
import com.therafaelreis.data.repository.ProjectsCache
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ProjectsCacheImpl @Inject constructor(
    private val projectsDatabase: ProjectsDatabase,
    private val mapper: CachedProjectMapper
) : ProjectsCache {

    override fun clearProjects(): Completable {
        return Completable.defer {
            projectsDatabase.cachedProjectsDAO().deleteProjects()
            Completable.complete()
        }
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        return Completable.defer {
            projectsDatabase.cachedProjectsDAO().insertProjects(projects.map {
                mapper.mapToCache(it)
            })
            Completable.complete()
        }
    }

    override fun getProjects(): Flowable<List<ProjectEntity>> {
        return projectsDatabase.cachedProjectsDAO().getProjects().map { cachedProjects ->
            cachedProjects.map {
                mapper.mapFromCached(it)
            }
        }
    }

    override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> {
        return projectsDatabase.cachedProjectsDAO().getBookmarkedProjects().toObservable()
            .map { cachedProjects ->
                cachedProjects.map {
                    mapper.mapFromCached(it)
                }
            }
    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        return Completable.defer {
            projectsDatabase.cachedProjectsDAO().setBookmarkStatus(true, projectId)
            Completable.complete()
        }
    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        return Completable.defer {
            projectsDatabase.cachedProjectsDAO().setBookmarkStatus(false, projectId)
            Completable.complete()
        }
    }

    override fun areProjectsCached(): Single<Boolean> {
        return projectsDatabase.cachedProjectsDAO().getProjects().first(emptyList()).map {
            it.isNotEmpty()
        }
    }

    override fun setLastCacheTime(lastCache: Long): Completable {
        return Completable.defer {
            projectsDatabase.configDAO().insertConfig(Config(lastCacheTime = lastCache))
            Completable.complete()
        }
    }

    override fun isProjectsCachedExpired(): Single<Boolean> {
        val currentTime = System.currentTimeMillis()
        val expirationInterval =(60 * 10 * 1_000).toLong() // 1 hour
        return  projectsDatabase.configDAO().getConfig()
            .toSingle(Config(lastCacheTime = 0))
            .map{
                currentTime - it.lastCacheTime > expirationInterval
            }
    }
}