package com.otakushelter.domain

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Translators : IntIdTable() {
    val name = varchar("name", 100).uniqueIndex()
}

class Translator(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Author>(Authors)

    var name by Authors.name
}