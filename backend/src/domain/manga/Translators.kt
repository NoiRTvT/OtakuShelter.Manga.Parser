package com.otakushelter.domain.manga

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Translators : IntIdTable() {
    val name = varchar("name", 100).uniqueIndex()
}

class Translator(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Translator>(Translators)

    var name by Translators.name
}