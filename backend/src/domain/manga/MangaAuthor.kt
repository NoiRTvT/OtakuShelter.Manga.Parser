package com.otakushelter.domain.manga

import org.jetbrains.exposed.sql.Table

object MangaAuthor : Table("manga_author") {
    val manga = reference("manga", Mangas).primaryKey(0)
    val authors = reference("authors", Authors).primaryKey(1)
}