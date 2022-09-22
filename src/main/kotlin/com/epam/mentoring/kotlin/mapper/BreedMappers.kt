package com.epam.mentoring.kotlin.mapper

import com.epam.mentoring.kotlin.data.model.Breed

fun Breed.toApiDto() = com.epam.mentoring.kotlin.api.dto.Breed(id, name, subBreeds.map { it.name })