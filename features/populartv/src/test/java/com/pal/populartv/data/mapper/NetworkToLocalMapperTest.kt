package com.pal.populartv.data.mapper

import com.pal.populartv.data.net.dto.TvShowDto
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class NetworkToLocalMapperTest {

    private val mapper: NetworkToLocalMapper = NetworkToLocalMapper()

    @Test
    fun `test mapper`() {
        val name = "name"
        val image = "image"
        val id = 1
        val score = "5"
        val page = 1

        val localEntity = mapper.mapFromRemote(Pair(TvShowDto(name, image, id, score), page))

        assert(localEntity.id == id)
        assert(localEntity.name == name)
        assert(localEntity.image == image)
        assert(localEntity.score == score)
        assert(localEntity.page == page)

        val noImage = mapper.mapFromRemote(Pair(TvShowDto(name, null, id, score), page))
        
        Assert.assertNotNull(noImage.image)
    }
    
}