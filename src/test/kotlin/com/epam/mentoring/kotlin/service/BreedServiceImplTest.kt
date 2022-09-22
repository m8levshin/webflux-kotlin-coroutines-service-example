package com.epam.mentoring.kotlin.service

import com.epam.mentoring.kotlin.data.model.Breed
import com.epam.mentoring.kotlin.data.repository.BreedRepository
import com.epam.mentoring.kotlin.mapper.toApiDto
import com.epam.mentoring.kotlin.remote.dogceo.dto.DogCeoClientImpl
import com.epam.mentoring.kotlin.testutils.Constants.BREED_IMAGE_URL_TEST
import com.epam.mentoring.kotlin.testutils.Constants.BREED_NAME_TEST
import com.epam.mentoring.kotlin.testutils.getRandomBreedEntity
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class BreedServiceImplTest {

    @MockK
    lateinit var breedRepository: BreedRepository

    @MockK
    lateinit var dogCeoClient: DogCeoClientImpl

    @InjectMockKs
    lateinit var sut: BreedServiceImpl

    @Test
    fun getAllBreeds() = runTest {

        mockkStatic(Breed::toApiDto)
        val firstBreed = getRandomBreedEntity()
        val secondBreed = getRandomBreedEntity()
        every { breedRepository.findAll() } returns listOf(firstBreed, secondBreed).asFlow()

        val sutResult = sut.getAllBreeds().toList()

        assertThat(sutResult).hasSize(2)
        assertThat(sutResult.map { it.id }).containsExactlyInAnyOrder(firstBreed.id, secondBreed.id)

        verify(exactly = 1) { firstBreed.toApiDto() }
        verify(exactly = 1) { secondBreed.toApiDto() }
    }

    @Test
    fun getImageByName_whenImageExistsInRepo() = runTest {
        coEvery { breedRepository.findBreedByName(eq(BREED_NAME_TEST)) } returns
                getRandomBreedEntity(name = BREED_NAME_TEST, img = BREED_IMAGE_URL_TEST)

        val sutResult = sut.getImageByName(BREED_NAME_TEST)

        assertThat(sutResult).isEqualTo(BREED_IMAGE_URL_TEST)
        coVerify(exactly = 0) { dogCeoClient.getBreedImages(any()) }
    }


    @Test
    fun getImageByName_whenImageNotExistsInRepo() = runTest {
        val breedEntity = getRandomBreedEntity(name = BREED_NAME_TEST, img = null)

        coEvery { breedRepository.findBreedByName(eq(BREED_NAME_TEST)) } returns breedEntity
        coEvery { dogCeoClient.getBreedImages(eq(BREED_NAME_TEST)) } returns
                listOf(BREED_IMAGE_URL_TEST, "secondImage")
        coEvery { breedRepository.save(any()) } returns breedEntity

        val sutResult = sut.getImageByName(BREED_NAME_TEST)

        assertThat(sutResult).isEqualTo(BREED_IMAGE_URL_TEST)
        coVerify(exactly = 1) { dogCeoClient.getBreedImages(eq(BREED_NAME_TEST)) }
        coVerify(exactly = 1) { breedRepository.save(any()) }
    }


}