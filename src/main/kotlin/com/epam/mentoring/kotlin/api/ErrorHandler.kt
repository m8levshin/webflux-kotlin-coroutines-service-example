package com.epam.mentoring.kotlin.api

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Component
class ErrorHandler : ErrorWebExceptionHandler {

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        val status: HttpStatus = when (ex) {
            is NotFoundException -> HttpStatus.NOT_FOUND
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
        exchange.response.statusCode = status
        return Mono.empty()
    }
}