package com.otakushelter

import com.otakushelter.controllers.healthCheck
import com.otakushelter.controllers.parseAllManga
import com.otakushelter.domain.entities.*
import com.otakushelter.extensions.getStringProperty
import com.otakushelter.utils.config
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.features.*
import io.ktor.routing.routing
import io.ktor.serialization.serialization
import io.ktor.util.KtorExperimentalAPI
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
fun Application.module() {
    val config = config(environment, "deployment")

    HttpClient(Apache)

    install(DefaultHeaders)
    install(CallLogging)
    install(ConditionalHeaders)
    install(Compression)
    install(ContentNegotiation) {
        serialization()
    }

    Database.connect(
        url = config.getStringProperty("database"),
        driver = config.getStringProperty("driver"),
        user = config.getStringProperty("user"),
        password = config.getStringProperty("password")
    )

    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.createMissingTablesAndColumns(
            Mangas,
            Authors,
            Chapters,
            MangaAuthor,
            MangaChapter,
            MangaTag,
            MangaTranslator,
            Pages,
            Tags,
            Translators,
            Types,
            ChapterPage
        )
    }

    routing {
        healthCheck()
        parseAllManga()
    }
}
