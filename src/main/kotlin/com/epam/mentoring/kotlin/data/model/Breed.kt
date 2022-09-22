package com.epam.mentoring.kotlin.data.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("breed")
class Breed(
    @Id
    var id: Long? = null,
    var name: String,
    var img: String? = null,
    var subBreeds: List<SubBreed>
) {}


data class SubBreed(val name: String)