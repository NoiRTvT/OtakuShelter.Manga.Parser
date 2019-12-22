package com.otakushelter.domain

import org.jetbrains.exposed.sql.Table

object MangaTranslator : Table("manga_translator") {
    val manga = reference("manga", Mangas).primaryKey(0)
    val translators = reference("translators", Translators).primaryKey(1)
}