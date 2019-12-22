package com.otakushelter.extensions

import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
fun ApplicationConfig.getStringProperty(property: String) = this.property(property).getString()