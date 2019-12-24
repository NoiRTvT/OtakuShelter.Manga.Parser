package com.otakushelter.extensions

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.SqlExpressionBuilder

fun <ID : Comparable<ID>, T : Entity<ID>> EntityClass<ID, T>.findFirst(op: SqlExpressionBuilder.() -> Op<Boolean>): T? =
    find(SqlExpressionBuilder.op()).firstOrNull()

fun <T> Iterable<T>.collectionSizeOrDefault(default: Int): Int = if (this is Collection<*>) this.size else default

fun <T, R> Iterable<T>.mapToSizedCollection(transform: (T) -> R): SizedCollection<R> {
    val newList = mapTo(ArrayList(collectionSizeOrDefault(10)), transform)
    return SizedCollection(newList)
}

fun <T> Iterable<T>.mapToSizedCollection(): SizedCollection<T> {
    val newList = mapTo(ArrayList(collectionSizeOrDefault(10)), { it })
    return SizedCollection(newList)
}