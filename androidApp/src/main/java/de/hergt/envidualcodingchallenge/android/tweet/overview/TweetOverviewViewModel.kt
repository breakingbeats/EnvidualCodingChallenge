package de.hergt.envidualcodingchallenge.android.tweet.overview

import androidx.lifecycle.ViewModel
import de.hergt.envidualcodingchallenge.core.NativeHttpClient
import de.hergt.envidualcodingchallenge.dtos.TweetDTO
import de.hergt.envidualcodingchallenge.extensions.toDTO
import de.hergt.envidualcodingchallenge.repositories.TweetRepository
import de.hergt.envidualcodingchallenge.utils.NetworkUtils
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TweetOverviewViewModel(
    private val repository: TweetRepository
): ViewModel() {
    private val mScope = MainScope()

    private val _mTweetItems: MutableStateFlow<List<TweetDTO>> = MutableStateFlow(listOf())
    val mTweetItems: StateFlow<List<TweetDTO>> = _mTweetItems.asStateFlow()

    init {
        mScope.launch {
            repository.getAllTweets().collect {list ->
                _mTweetItems.emit(list.map { it.toDTO() })
            }
        }
    }

    suspend fun fetchTweets() {
        try {
            repository.fetchTweets()
        } catch (error: Error) {
            println(error.message)
        }
    }

}