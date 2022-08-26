package my.noveldokusha.ui.screens.reader

import my.noveldokusha.tools.BookTextMapper

sealed interface ReaderItem {
    val chapterUrl: String
    val chapterIndex: Int

    interface Position {
        val chapterItemIndex: Int
    }

    interface Text {
        val text: String
        val textTranslated: String?
        val textToDisplay get() = textTranslated ?: text
    }

    enum class LOCATION { FIRST, MIDDLE, LAST }

    data class GoogleTranslateAttribution(
        override val chapterUrl: String,
        override val chapterIndex: Int,
    ) : ReaderItem

    data class Title(
        override val chapterUrl: String,
        override val chapterIndex: Int,
        override val chapterItemIndex: Int,
        override val text: String,
        override val textTranslated: String? = null
    ) : ReaderItem, Position, Text

    data class Body(
        override val chapterUrl: String,
        override val chapterIndex: Int,
        override val chapterItemIndex: Int,
        override val text: String,
        val location: LOCATION,
        override val textTranslated: String? = null
    ) : ReaderItem, Position, Text

    data class Image(
        override val chapterUrl: String,
        override val chapterIndex: Int,
        override val chapterItemIndex: Int,
        val text: String,
        val location: LOCATION,
        val image: BookTextMapper.ImgEntry
    ) : ReaderItem, Position

    class Translating(
        override val chapterUrl: String,
        override val chapterIndex: Int,
        val sourceLang: String,
        val targetLang: String
    ) : ReaderItem

    class Progressbar(override val chapterUrl: String, override val chapterIndex: Int) :
        ReaderItem

    class Divider(override val chapterUrl: String, override val chapterIndex: Int) : ReaderItem
    class BookEnd(override val chapterUrl: String, override val chapterIndex: Int) : ReaderItem
    class BookStart(override val chapterUrl: String, override val chapterIndex: Int) : ReaderItem
    class Error(
        override val chapterUrl: String,
        override val chapterIndex: Int,
        val text: String
    ) : ReaderItem

    class Padding(override val chapterUrl: String, override val chapterIndex: Int) : ReaderItem
}

