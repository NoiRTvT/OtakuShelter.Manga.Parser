package com.otakushelter.controllers

import com.otakushelter.domain.entities.*
import com.otakushelter.domain.parser.*
import com.otakushelter.extensions.findFirst
import io.ktor.routing.Routing
import io.ktor.routing.get
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction

val parser = Parser()

fun Routing.parseAllManga() {
    get("/api/parser/parse-all") {
        val pagination = parser.parseByUrl(Parser.catalog) { parsePagination(it) }
        for (paginationItem in pagination) {
            val mangaList = parser.parseByUrl(paginationItem) { parseMangaList(it) }
            for (mangaListItem in mangaList) {
                parseMangaToDatabase(mangaListItem)
            }
        }
    }
}

fun parseMangaToDatabase(mangaListItem: String) {
    parser.setDocumentByURL(mangaListItem)

    transaction {
        val parsedName = parser.parseByDocument { parseName(it) }

        if (!Manga.find { Mangas.name eq parsedName }.empty())
            close()

        val parsedType = parser.parseByDocument {
            parseType(it).let {
                Type.findFirst { Types.name eq it } ?: Type.new {
                    name = it
                }
            }
        }
        val parsedTags = SizedCollection(parser.parseByDocument { parseTags(it) }.map {
            Tag.findFirst { Tags.name eq it } ?: Tag.new { name = it }
        })
        val parsedTranslators = SizedCollection(parser.parseByDocument { parseTranslators(it) }.map {
            Translator.findFirst { Translators.name eq it } ?: Translator.new { name = it }
        })
        val parsedAuthors = SizedCollection(parser.parseByDocument { parseAuthors(it) }.map {
            Author.findFirst { Authors.name eq it } ?: Author.new { name = it }
        })
        val parsedChapters = SizedCollection(parser.parseByDocument { parseChapters(it) })

        val manga = Manga.new {
            name = parsedName
            anotherName = parser.parseByDocument { parseAnotherTitle(it) }
            description = parser.parseByDocument { parseDescription(it) }
            image = parser.parseByDocument { parseImage(it) }
            status = parser.parseByDocument { parseStatus(it) }
            type = parsedType
        }
        transaction {
            manga.tags = parsedTags
            manga.translators = parsedTranslators
            manga.authors = parsedAuthors
            manga.chapters = parsedChapters
        }
    }
}