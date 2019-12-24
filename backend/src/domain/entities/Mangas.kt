package com.otakushelter.domain.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Mangas : IntIdTable() {
    val name = varchar("name", 500).uniqueIndex()
    val anotherName = varchar("another_name", 500)
    val description = varchar("description", 5000)
    val image = varchar("image", 300)
    val status = varchar("status", 100)
    val sourceURL = varchar("source_url", 300)
    val type = reference("type", Types)
}

class Manga(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Manga>(Mangas)

    var name by Mangas.name
    var anotherName by Mangas.anotherName
    var description by Mangas.description
    var image by Mangas.image
    var status by Mangas.status
    var sourceURL by Mangas.sourceURL
    var type by Type referencedOn Mangas.type
    var tags by Tag via MangaTag
    var chapters by Chapter via MangaChapter
    var translators by Translator via MangaTranslator
    var authors by Author via MangaAuthor
}