package com.otakushelter.domain.parser

import org.jsoup.nodes.Document

fun parseAnotherTitle(document: Document) = document
    .select(".mangatitle .item2")
    .filterIndexed { index, _ -> index == 0 }
    .first().child(0).text()