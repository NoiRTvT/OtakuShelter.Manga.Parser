package com.otakushelter.domain.parser

import org.jsoup.nodes.Document

fun parseAuthors(document: Document) = document
    .select(".mangatitle .item2")
    .filterIndexed { index, _ -> index == 2 }.first()
    .select("a")
    .filter { !it.text().isNullOrEmpty() }
    .map { it.text() }
    .distinct()