package com.epam.mentoring.kotlin.data.repository

import com.epam.mentoring.kotlin.data.model.Breed
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BreedRepository : CoroutineCrudRepository<Breed, Long> {
    suspend fun findBreedByName(name: String): Breed?
}