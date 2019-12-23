package com.otakushelter.domain.parser

import org.jsoup.nodes.Document

fun parseName(document: Document) = document
    .select(".name_row .title_top_a").text()