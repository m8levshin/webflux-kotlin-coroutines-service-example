package com.epam.mentoring.kotlin.service

import com.epam.mentoring.kotlin.api.dto.Breed
import kotlinx.coroutines.flow.Flow

interface BreedService {
    suspend fun getAllBreeds(): Flow<Breed>
    suspend fun getImageByName(breedName: String): String?
}