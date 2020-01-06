package com.otakushelter.domain.manga

import org.jetbrains.exposed.sql.Table

object MangaTag : Table("manga_tag") {
    val manga = reference("manga", Mangas).primaryKey(0)
    val tag = reference("tag", Tags).primaryKey(1)
}
