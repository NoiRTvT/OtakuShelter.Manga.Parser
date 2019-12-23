package com.otakushelter.domain.parser

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class Parser {
    private lateinit var document: Document

    fun <R> parseByUrl(url: String, function: (Document) -> R): R {
        document = Jsoup.connect(url).get()
        return function(document)
    }

    fun setDocumentByURL(url: String): Parser {
        document = Jsoup.connect(url).get()
        return this
    }

    fun <R> parseByDocument(function: (Document) -> R): R = function(document)

    companion object {
        const val baseURL = "https://manga-chan.me"
        const val catalog = "$baseURL/catalog"
    }
}