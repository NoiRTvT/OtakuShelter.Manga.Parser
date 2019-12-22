package com.otakushelter.domain

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Types : IntIdTable() {
    val name = varchar("name", 100).uniqueIndex()
}

class Type(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Tag>(Types)

    var name by Types.name
}