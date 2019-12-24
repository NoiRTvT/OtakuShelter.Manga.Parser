package com.otakushelter.controllers

import com.otakushelter.domain.entities.*
import com.otakushelter.domain.parser.*
import com.otakushelter.extensions.findFirst
import com.otakushelter.extensions.mapToSizedCollection
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post
import org.jetbrains.exposed.sql.transactions.transaction
import org.jsoup.nodes.Document

val parser = Parser()

fun Routing.parseAllManga() {
    post("/api/parser/parse-all") {
        call.respond("Parsing all manga to be started.")
        startParse()
    }
}

fun startParse() {
    val pagination = parser.parseByUrl(Parser.catalog) { parsePagination(it) }
    for (paginationItem in pagination) {
        val mangaList = parser.parseByUrl(paginationItem) { parseMangaList(it) }
        for (mangaListItem in mangaList) {
            parser.parseByUrl(mangaListItem) {
                parseMangaToDatabase(it)
            }
        }
    }
}

fun parseMangaToDatabase(document: Document) = transaction {
    val parsedName = parseName(document)
    if (Manga.findFirst { Mangas.name eq parsedName } != null) return@transaction
    val parsedType = parseType(document).let {
        Type.findFirst { Types.name eq it } ?: Type.new { name = it }
    }
    val parsedTags = parseTags(document).mapToSizedCollection {
        Tag.findFirst { Tags.name eq it } ?: Tag.new { name = it }
    }
    val parsedTranslators = parseTranslators(document).mapToSizedCollection {
        Translator.findFirst { Translators.name eq it } ?: Translator.new { name = it }
    }
    val parsedAuthors = parseAuthors(document).mapToSizedCollection {
        Author.findFirst { Authors.name eq it } ?: Author.new { name = it }
    }
    val parsedChapters = parseChapters(document)
        .mapToSizedCollection()
    parsedChapters.forEach { chapter ->
        val pages = parser
            .parseByUrl(chapter.sourceURL) { parsePages(it) }
            .mapToSizedCollection()
        chapter.pages = pages
    }
    val manga = Manga.new {
        name = parsedName
        anotherName = parseAnotherTitle(document)
        description = parseDescription(document)
        image = parseImage(document)
        status = parseStatus(document)
        sourceURL = parseSourceURL(document)
        type = parsedType
    }
    manga.tags = parsedTags
    manga.translators = parsedTranslators
    manga.authors = parsedAuthors
    manga.chapters = parsedChapters
}