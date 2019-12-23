package com.otakushelter.domain.parser

import org.jsoup.nodes.Document

fun parsePagination(document: Document) = document
    .select("#pagination span a")
    .map { Parser.catalog + it.attr("href") }
    .let { listOf("${Parser.catalog}?offset=0") + it }