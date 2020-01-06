package com.otakushelter.domain.parser

import com.otakushelter.domain.manga.Chapter
import org.joda.time.DateTime
import org.jsoup.nodes.Document

fun parseChapters(document: Document) = document
    .select(".table_cha tr")
    .filter { it.attr("class") == "no_zaliv" || it.attr("class") == "zaliv" }
    .reversed()
    .mapIndexed { index, it ->
        Chapter.new {
            name = it.child(0).child(0).child(0).text()
            uploadDate = DateTime(it.child(it.children().size - 1).child(0).text())
            order = index
            sourceURL = Parser.baseURL + it.child(0).child(0).child(0).attr("href")
        }
    }

