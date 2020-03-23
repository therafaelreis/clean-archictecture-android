package com.therafaelreis.data.mapper

import com.therafaelreis.data.model.ProjectEntity
import com.therafaelreis.data.test.factory.ProjectFactory
import com.therafaelreis.domain.model.Project
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProjectMapperTest {

    private val mapper = ProjectMapper()

    @Test
    fun mapFromEntityMapsData(){
        val entity = ProjectFactory.makeProjectEntity()
        val model = mapper.mapFromEntity(entity)

        assertEqualData(entity, model)
    }

    @Test
    fun mapToEntityMapsData(){
        val model = ProjectFactory.makeProject()
        val entity = mapper.mapToEntity(model)

        assertEqualData(entity, model)
    }

    private fun assertEqualData(entity: ProjectEntity,
                                model: Project){
        assertEquals(entity.id, model.id)
        assertEquals(entity.name, model.name)
        assertEquals(entity.fullName, model.fullName)
        assertEquals(entity.starCount, model.starCount)
        assertEquals(entity.dateCreated, model.dateCreated)
        assertEquals(entity.ownerName, model.ownerName)
        assertEquals(entity.ownerAvatar, model.ownerAvatar)
        assertEquals(entity.isBookmarked, model.isBookmarked)
    }
}