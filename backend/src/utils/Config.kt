package com.otakushelter.utils

import com.otakushelter.exceptions.ConfigurationNotFoundException
import com.typesafe.config.ConfigFactory
import io.ktor.application.ApplicationEnvironment
import io.ktor.config.ApplicationConfig
import io.ktor.config.HoconApplicationConfig
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
fun config(environment: ApplicationEnvironment, firstOrderProperty: String): ApplicationConfig {
    val envKind = environment.config.propertyOrNull("ktor.environment")?.getString()
        ?: throw ConfigurationNotFoundException("Property environment not be found")
    val isDev = envKind == "dev"
    val isProd = envKind == "prod"
    return when {
        isDev ->
            HoconApplicationConfig(ConfigFactory.load("applicationDev.conf")).config("ktor.$firstOrderProperty")
        isProd ->
            environment.config.config("ktor.$firstOrderProperty")
        else ->
            throw ConfigurationNotFoundException("Property environment is incorrect.")
    }
}