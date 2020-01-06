package com.otakushelter.domain.manga

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Tags : IntIdTable() {
    val name = varchar("name", 100).uniqueIndex()
}

class Tag(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Tag>(Tags)

    var name by Tags.name
}