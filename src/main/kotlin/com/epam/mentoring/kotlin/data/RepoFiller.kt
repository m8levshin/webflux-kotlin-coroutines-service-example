package com.epam.mentoring.kotlin.data

import com.epam.mentoring.kotlin.data.model.Breed
import com.epam.mentoring.kotlin.data.model.SubBreed
import com.epam.mentoring.kotlin.data.repository.BreedRepository
import com.epam.mentoring.kotlin.remote.dogceo.dto.DogCeoClient
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class RepoFiller(
    private val dogCeoClient: DogCeoClient,
    private val breedRepository: BreedRepository
) {
    @PostConstruct
    fun fillDatabaseIfNeeded() = runBlocking {
        if (breedRepository.count() == 0L) {
            dogCeoClient
                .getAllBreeds()
                .map {
                    Breed(
                        name = it.key,
                        subBreeds = it.value?.map { subBreedString -> SubBreed(subBreedString) } ?: listOf()
                    )
                }
                .let { breedRepository.saveAll(it) }
                .last()
        }
    }

}