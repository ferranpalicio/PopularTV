package com.pal.populartv.data.mapper

import com.pal.populartv.data.net.ApiConstants
import com.playground.database.entities.TvShowRoomEntity
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class DatabaseToEntityMapperTest {

    private val mapper = DatabaseToEntityMapper()

    @Test
    fun `test mapper`() {
        val id = 1
        val name = "name"
        val image = "image"
        val score = "score"
        val entity = mapper.mapFromRemote(TvShowRoomEntity(id, name, image, score, 1))

        assert(entity.id == id)
        assert(entity.name == name)
        assert(entity.image.contains(ApiConstants.BASE_IMAGE_URL))
        assert(entity.score == score)
    }
}