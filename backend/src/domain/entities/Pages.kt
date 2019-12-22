package com.otakushelter.domain.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Pages : IntIdTable() {
    val order = integer("order")
    val image = varchar("image", 300)
}

class Page(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Page>(Pages)

    var order by Pages.order
    var image by Pages.image
}