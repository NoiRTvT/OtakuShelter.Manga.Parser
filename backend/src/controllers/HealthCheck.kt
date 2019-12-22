package com.otakushelter.controllers

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get

fun Routing.healthCheck() {
    get("/health/check") {
        call.respond("OK")
    }
}