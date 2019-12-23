package com.otakushelter.domain.parser

import org.jsoup.nodes.Document

fun parseImage(document: Document) = document
    .select("#cover")
    .attr("src")