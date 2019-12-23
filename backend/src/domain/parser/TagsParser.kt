package com.otakushelter.domain.parser

import org.jsoup.nodes.Document

fun parseTags(document: Document) = document
    .select(".mangatitle .item2")
    .filterIndexed { index, _ -> index == 5 }
    .first()
    .select("a")
    .filter { !it.text().isNullOrEmpty() }
    .map {
        it.text().replace("_", " ")[0].toUpperCase() +
                it.text().replace("_", " ").substring(1)
    }