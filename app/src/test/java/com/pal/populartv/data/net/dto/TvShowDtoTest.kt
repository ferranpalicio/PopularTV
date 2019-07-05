package com.pal.populartv.data.net.dto

import com.pal.populartv.data.net.ApiConstants
import org.junit.Assert.*
import org.junit.Test

class TvShowDtoTest {

    @Test
    fun mapper_wors_as_expected() {
        val tvShowDto = TvShowDto("name", "image", 1, "4.5")
        val (id, name, image, score) = tvShowDto.toDomain()
        assertEquals(id, 1)
        assertEquals(name, "name")
        assertEquals(score, "4.5")
        assertEquals(image, ApiConstants.BASE_IMAGE_URL + "image")
    }
}