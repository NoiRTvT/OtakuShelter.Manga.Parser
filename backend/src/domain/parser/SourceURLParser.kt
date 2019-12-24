package com.otakushelter.domain.parser

import org.jsoup.nodes.Document

fun parseSourceURL(document: Document) = document
    .select(".name_row .title_top_a").attr("href")