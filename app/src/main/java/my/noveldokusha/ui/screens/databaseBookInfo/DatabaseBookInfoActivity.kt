package my.noveldokusha.ui.screens.databaseBookInfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import my.noveldokusha.composableActions.SetSystemBarTransparent
import my.noveldokusha.data.BookMetadata
import my.noveldokusha.scraper.DatabaseInterface
import my.noveldokusha.scraper.SearchGenre
import my.noveldokusha.ui.BaseActivity
import my.noveldokusha.ui.goToDatabaseBookInfo
import my.noveldokusha.ui.goToDatabaseSearchGenres
import my.noveldokusha.ui.goToGlobalSearch
import my.noveldokusha.ui.theme.Theme
import my.noveldokusha.utils.Extra_String

@AndroidEntryPoint
class DatabaseBookInfoActivity : BaseActivity() {
    class IntentData : Intent, DatabaseBookInfoStateBundle {
        override var databaseUrlBase by Extra_String()
        override var bookUrl by Extra_String()
        override var bookTitle by Extra_String()

        constructor(intent: Intent) : super(intent)
        constructor(ctx: Context, databaseUrlBase: String, bookMetadata: BookMetadata) : super(
            ctx,
            DatabaseBookInfoActivity::class.java
        ) {
            this.databaseUrlBase = databaseUrlBase
            this.bookUrl = bookMetadata.url
            this.bookTitle = bookMetadata.title
        }
    }

    private val viewModel by viewModels<DatabaseBookInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val scrollState = rememberScrollState()
            val alpha by remember {
                derivedStateOf {
                    val value = (scrollState.value - 50).coerceIn(0, 200).toFloat()
                    val maxvalue = (scrollState.maxValue).coerceIn(1, 200).toFloat()
                    value / maxvalue
                }
            }
            Theme(appPreferences = appPreferences) {
                SetSystemBarTransparent(alpha)
                DatabaseBookInfoScreen(
                    scrollState = scrollState,
                    data = viewModel.bookData,
                    onSourcesClick = ::openGlobalSearchPage,
                    onAuthorsClick = ::openSearchPageByAuthor,
                    onGenresClick = ::openSearchPageByGenres,
                    onBookClick = ::openBookInfo
                )
            }
        }
    }

    private fun openGlobalSearchPage() = goToGlobalSearch(text = viewModel.bookMetadata.title)

    private fun openSearchPageByAuthor(author: DatabaseInterface.AuthorMetadata) {
        // TODO
    }

    private fun openSearchPageByGenres(genres: List<SearchGenre>) = goToDatabaseSearchGenres(
        includedGenresIds = genres.map { it.id },
        databaseUrlBase = viewModel.database.baseUrl
    )

    private fun openBookInfo(book: BookMetadata) = goToDatabaseBookInfo(
        book = book,
        databaseUrlBase = viewModel.database.baseUrl
    )
}
