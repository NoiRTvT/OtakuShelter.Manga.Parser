package com.otakushelter.domain.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Chapters : IntIdTable() {
    val name = varchar("name", 100).uniqueIndex()
    val order = integer("order")
    val uploadDate = date("upload_date")
    val pages = reference("page", Pages)
}

class Chapter(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Author>(Authors)

    var name by Authors.name
    var order by Chapters.order
    var uploadDate by Chapters.uploadDate
    var pages by Page referencedOn Chapters.pages
}