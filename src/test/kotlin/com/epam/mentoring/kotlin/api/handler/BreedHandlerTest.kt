package com.epam.mentoring.kotlin.api.handler

import com.epam.mentoring.kotlin.api.dto.Breed
import com.epam.mentoring.kotlin.api.dto.BreedImageResponse
import com.epam.mentoring.kotlin.config.RouterConfig
import com.epam.mentoring.kotlin.data.repository.BreedRepository
import com.epam.mentoring.kotlin.mapper.toApiDto
import com.epam.mentoring.kotlin.service.BreedService
import com.epam.mentoring.kotlin.testutils.Constants.BREED_IMAGE_URL_TEST
import com.epam.mentoring.kotlin.testutils.Constants.BREED_NAME_TEST
import com.epam.mentoring.kotlin.testutils.getRandomBreedEntity
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.reactive.server.WebTestClient


@WebFluxTest
@Import(RouterConfig::class, BreedHandler::class)
class BreedHandlerTest(@Autowired val webClient: WebTestClient) {

    @MockkBean
    lateinit var breedService: BreedService

    @MockkBean
    lateinit var breedRepository: BreedRepository

    @Test
    fun getAllBreeds() = runTest() {
        val dtoList = listOf(getRandomBreedEntity().toApiDto(), getRandomBreedEntity().toApiDto())

        coEvery { breedService.getAllBreeds() } returns dtoList.asFlow()

        val reqResult = webClient.get().uri("/breeds").exchange()

        reqResult.expectStatus().is2xxSuccessful
        reqResult.expectBodyList(Breed::class.java).hasSize(2).contains(dtoList[0], dtoList[1])
    }

    @Test
    fun getBreedImage_whenImageExists() {
        coEvery { breedService.getImageByName(eq(BREED_NAME_TEST)) } returns BREED_IMAGE_URL_TEST

        val reqResult = webClient.get().uri("/breeds/$BREED_NAME_TEST/image").exchange()

        reqResult.expectStatus().is2xxSuccessful
        reqResult
            .expectBody(BreedImageResponse::class.java)
            .consumeWith {
                assertThat(it.responseBody!!.url).isEqualTo(BREED_IMAGE_URL_TEST)
            }
    }

    @Test
    fun getBreedImage_whenImageDoesntExists() {
        coEvery { breedService.getImageByName(eq(BREED_NAME_TEST)) } returns null

        val reqResult = webClient.get().uri("/breeds/$BREED_NAME_TEST/image").exchange()

        reqResult.expectStatus().isNotFound
    }
}