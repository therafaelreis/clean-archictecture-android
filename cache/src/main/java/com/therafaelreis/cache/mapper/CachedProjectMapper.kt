package com.therafaelreis.cache.mapper

import com.therafaelreis.cache.model.CachedProject
import com.therafaelreis.data.model.ProjectEntity

class CachedProjectMapper : CacheMapper<CachedProject, ProjectEntity> {

    override fun mapFromCached(type: CachedProject): ProjectEntity {
        return ProjectEntity(
            type.id,
            type.name,
            type.fullName,
            type.starCount,
            type.dateCreated,
            type.ownerName,
            type.ownerAvatar,
            type.isBookmarked
        )
    }

    override fun mapToCache(type: ProjectEntity): CachedProject {
        return CachedProject( type.id,
            type.name,
            type.fullName,
            type.starCount,
            type.dateCreated,
            type.ownerName,
            type.ownerAvatar,
            type.isBookmarked)
    }

}