package com.otakushelter.domain.parser

import org.jsoup.nodes.Document

fun parseMangaList(document: Document) = document
    .select(".content_row")
    .map { Parser.baseURL + it.child(0).child(0).attr("href") }