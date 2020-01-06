package com.otakushelter.domain.parser

import com.otakushelter.domain.manga.Page
import org.jsoup.nodes.Document

fun parsePages(document: Document): List<Page> {
    val current = document.select("center").prev().html();
    val firstIndex = current.indexOf("\"fullimg\":[") + 11;
    val secondIndex = current.indexOf(",]");
    val stringArray = current.substring(firstIndex, secondIndex).split(",");
    return stringArray.mapIndexed { index, it ->
        Page.new {
            image = it.replace("\"", "")
            order = index
        }
    };
}