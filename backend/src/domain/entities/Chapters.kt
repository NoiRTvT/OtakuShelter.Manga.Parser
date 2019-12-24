package com.otakushelter.domain.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Chapters : IntIdTable() {
    val name = varchar("name", 100).uniqueIndex()
    val order = integer("order")
    val uploadDate = date("upload_date")
    val sourceURL = varchar("source_url", 300)
}

class Chapter(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Chapter>(Chapters)

    var name by Chapters.name
    var order by Chapters.order
    var uploadDate by Chapters.uploadDate
    var sourceURL by Chapters.sourceURL
    var pages by Page via ChapterPage
}