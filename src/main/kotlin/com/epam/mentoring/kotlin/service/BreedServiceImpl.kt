package com.epam.mentoring.kotlin.service

import com.epam.mentoring.kotlin.data.repository.BreedRepository
import com.epam.mentoring.kotlin.mapper.toApiDto
import com.epam.mentoring.kotlin.remote.dogceo.dto.DogCeoClient
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service

@Service
class BreedServiceImpl(
    private val breedRepository: BreedRepository,
    private val dogCeoClient: DogCeoClient
) : BreedService {

    override suspend fun getAllBreeds() = breedRepository.findAll().map { it.toApiDto() }

    override suspend fun getImageByName(breedName: String): String? {
        val breed = breedRepository.findBreedByName(breedName) ?: return null
        if (breed.img == null) {
            val breedImages = dogCeoClient.getBreedImages(breedName)
            if (breedImages.isNotEmpty()) {
                breed.img = breedImages.first()
                breedRepository.save(breed)
            }
        }
        return breed.img
    }
}