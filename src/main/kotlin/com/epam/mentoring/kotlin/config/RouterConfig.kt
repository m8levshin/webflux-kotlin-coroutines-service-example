package com.epam.mentoring.kotlin.config

import com.epam.mentoring.kotlin.api.handler.BreedHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RouterConfig {

    @Bean
    fun productRoutes(breedHandler: BreedHandler) = coRouter {
        GET("/breeds", breedHandler::getAllBreeds)
        GET("/breeds/{name}/image", breedHandler::getBreedImage)
    }
}