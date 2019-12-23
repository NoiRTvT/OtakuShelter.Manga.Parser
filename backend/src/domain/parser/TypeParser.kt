package com.otakushelter.domain.parser

import org.jsoup.nodes.Document

fun parseType(document: Document) = document
    .select(".mangatitle .item2")
    .filterIndexed { index, _ -> index == 1 }.first()
    .selectFirst("a").text()