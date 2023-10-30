package de.hergt.envidualcodingchallenge.core.compose

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.window.ComposeUIViewController
import de.hergt.envidualcodingchallenge.dtos.TweetDTO
import de.hergt.envidualcodingchallenge.ui.TweetOverview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import platform.UIKit.UIViewController

object SharedViewControllers {

    private data class ViewState(val status: List<TweetDTO> = listOf())
    private val state = MutableStateFlow(ViewState())

    fun statusComposable(click: () -> Unit): UIViewController {
        return ComposeUIViewController {
            with(state.collectAsState().value) {
                TweetOverview(status)
            }
        }
    }

    fun updateTweetOverviewComposable(tweets: List<TweetDTO>) {
        state.update { it.copy(status = tweets) }
    }
}