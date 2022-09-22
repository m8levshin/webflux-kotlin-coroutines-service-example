package com.epam.mentoring.kotlin.remote.dogceo.dto

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody


@Component
class DogCeoClientImpl(val webClient: WebClient) : DogCeoClient {

    companion object {
        private const val ALL_BREEDS_URL = "https://dog.ceo/api/breeds/list/all";
        private const val IMAGES_OF_BREED_URL = "https://dog.ceo/api/breed/%s/images"
    }

    override suspend fun getBreedImages(name: String): List<String> {
        return webClient.get()
            .uri(IMAGES_OF_BREED_URL.format(name))
            .retrieve()
            .awaitBody<BreedImagesDogCeoResponse>()
            .message
    }

    override suspend fun getAllBreeds(): Map<String, List<String>?> {
        return webClient.get()
            .uri(ALL_BREEDS_URL)
            .retrieve()
            .awaitBody<AllBreedsDogCeoResponse>()
            .message
    }

}