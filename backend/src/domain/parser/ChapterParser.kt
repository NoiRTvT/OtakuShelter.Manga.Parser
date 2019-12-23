package com.otakushelter.domain.parser

import com.otakushelter.domain.entities.Chapter
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

fun parseChapters(document: Document) = document
    .select(".table_cha tr")
    .filter { it.attr("class") == "no_zaliv" || it.attr("class") == "zaliv" }
    .reversed()
    .mapIndexed { index, it -> some(index, it) }

fun some(index: Int, it: Element): Chapter {
    val pages = transaction {
        Parser().parseByUrl(Parser.baseURL + it.child(0).child(0).child(0).attr("href")) {
            parsePages(it)
        }
    }
    val chapter = transaction {
        Chapter.new {
            name = it.child(0).child(0).child(0).text()
            uploadDate = DateTime(it.child(it.children().size - 1).child(0).text())
            order = index
        }
    }
    transaction {
        chapter.pages = SizedCollection(pages)
    }
    return chapter
}
