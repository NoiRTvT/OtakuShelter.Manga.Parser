package com.otakushelter.domain.entities

import org.jetbrains.exposed.sql.Table

object ChapterPage : Table("chapter_page") {
    val chapter = reference("chapter", Chapters).primaryKey(0)
    val page = reference("page", Pages).primaryKey(1)
}