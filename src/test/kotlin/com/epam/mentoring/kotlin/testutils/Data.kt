package com.epam.mentoring.kotlin.testutils

import com.epam.mentoring.kotlin.data.model.Breed
import com.epam.mentoring.kotlin.data.model.SubBreed
import io.mockk.InternalPlatformDsl.toStr
import kotlin.random.Random

object Constants {
    const val BREED_NAME_TEST = "breed"
    const val BREED_IMAGE_URL_TEST = "http://test.com/test.png"
}

fun getRandomBreedEntity(
    id: Long? = null,
    name: String? = null,
    subBreed: List<SubBreed>? = null,
    img: String? = null
) = Breed(
    id = id ?: Random.nextLong(),
    name = name ?: Random.nextLong().toStr(),
    subBreeds = subBreed ?: emptyList(),
    img = img
)