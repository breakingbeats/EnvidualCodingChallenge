package de.hergt.envidualcodingchallenge.android.tweet.overview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.hergt.envidualcodingchallenge.ui.TweetOverview
import kotlinx.coroutines.launch

@Composable
fun TweetOverviewView(
    viewModel: TweetOverviewViewModel
) {
    val mScope = rememberCoroutineScope()
    val mTweets = viewModel.mTweetItems.collectAsStateWithLifecycle()

    SideEffect {
        mScope.launch {
            viewModel.fetchTweets()
        }
    }

    TweetOverview(tweets = mTweets.value)
}