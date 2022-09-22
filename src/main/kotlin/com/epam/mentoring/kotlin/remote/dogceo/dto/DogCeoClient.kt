package com.epam.mentoring.kotlin.remote.dogceo.dto

interface DogCeoClient {
    suspend fun getBreedImages(name: String): List<String>
    suspend fun getAllBreeds(): Map<String, List<String>?>
}