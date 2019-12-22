package com.otakushelter.domain

import org.jetbrains.exposed.sql.Table

object MangaChapter : Table("manga_chapter") {
    val manga = reference("manga", Mangas).primaryKey(0)
    val chapter = reference("chapter", Chapters).primaryKey(1)
}