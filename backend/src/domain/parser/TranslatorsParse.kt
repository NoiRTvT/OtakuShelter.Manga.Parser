package com.otakushelter.domain.parser

import org.jsoup.nodes.Document

fun parseTranslators(document: Document) = document
    .select(".mangatitle .item2")
    .filterIndexed { index, _ -> index == 6 }.first()
    .select("a")
    .filter { !it.text().isNullOrEmpty() }
    .map { it.text() }
    .distinct()