package com.epam.mentoring.kotlin.api.handler

import com.epam.mentoring.kotlin.api.dto.BreedImageResponse
import com.epam.mentoring.kotlin.service.BreedService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.*

@Service
class BreedHandler(private val breedService: BreedService) {

    suspend fun getAllBreeds(request: ServerRequest) =
        ServerResponse.ok().json().bodyAndAwait(breedService.getAllBreeds())

    suspend fun getBreedImage(request: ServerRequest): ServerResponse {
        val breedImage = breedService.getImageByName(request.pathVariable("name")) ?: throw NotFoundException()
        return ServerResponse.ok().json().bodyValueAndAwait(BreedImageResponse(breedImage))
    }

}