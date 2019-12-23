package com.otakushelter.domain.parser

import org.jsoup.nodes.Document

fun parseDescription(document: Document) = document
    .select("#description").text()
    .replace("Прислать описание", "").trim();