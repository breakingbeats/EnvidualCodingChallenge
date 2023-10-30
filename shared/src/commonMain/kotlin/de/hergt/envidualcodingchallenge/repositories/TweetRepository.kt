package de.hergt.envidualcodingchallenge.repositories

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import de.hergt.envidualcodingchallenge.CommonDatabase
import de.hergt.envidualcodingchallenge.Tweet
import de.hergt.envidualcodingchallenge.TweetQueries
import de.hergt.envidualcodingchallenge.core.DriverFactory
import de.hergt.envidualcodingchallenge.core.NativeHttpClient
import de.hergt.envidualcodingchallenge.utils.TimeUtils
import de.hergt.envidualcodingchallenge.core.createDatabase
import de.hergt.envidualcodingchallenge.dtos.TweetDTO
import de.hergt.envidualcodingchallenge.utils.NetworkUtils
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.utils.io.core.use
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TweetRepository(driverFactory: DriverFactory) {

    val apiPath = "/tweet"

    private lateinit var mDatabase: CommonDatabase
    private lateinit var mTweetTableQueries: TweetQueries

    private val mTimeUtils = TimeUtils()
    private val mHttpClient = NativeHttpClient().mClient

    init {
        CoroutineScope(Dispatchers.Default).launch {
            mDatabase = async { createDatabase(driverFactory) }.await()
            mTweetTableQueries = mDatabase.tweetQueries

            if (mTweetTableQueries.getAllTweets().executeAsList().isEmpty()) {
                mTweetTableQueries.setTweetByValues(
                    null,
                    "Elon Musk baut zweite Giga-Factory",
                    mTimeUtils.mCurrentMillis
                )
                mTweetTableQueries.setTweetByValues(
                    null,
                    "Kotlin Multiplatform is BETA",
                    mTimeUtils.mCurrentMillis
                )
                mTweetTableQueries.setTweetByValues(
                    null,
                    "Kotlin 1.9.20-RC steht bereit",
                    mTimeUtils.mCurrentMillis
                )
            }
        }
    }

    @Throws(Exception::class)
    suspend fun fetchTweets() {
        val fetchedTweets = try {
            mHttpClient.use { client ->
                client.get(NetworkUtils.mLatestApiPath.plus(apiPath)).body<List<TweetDTO>>()
            }
        } catch (error: Exception) {
            null
        }

        fetchedTweets?.let { fetchedListOfTweets ->
            deleteAllTweets()
            fetchedListOfTweets.forEach {
                createTweet(it.mMessage)
            }
        }
    }

    fun getAllTweets(): Flow<List<Tweet>> {
        return mTweetTableQueries.getAllTweets()
            .asFlow()
            .mapToList(Dispatchers.Default)
    }

    fun getTweetsByKeyword(keyword: String): Flow<List<Tweet>> {
        val query = if (keyword.isNotEmpty() && keyword.isNotBlank()) {
            mTweetTableQueries.getAllTweetsByKeyword(keyword)
        } else {
            mTweetTableQueries.getAllTweets()
        }
        return query.asFlow().mapToList(Dispatchers.Default)
    }

    fun getTweetById(id: Long): Flow<Tweet?> {
        return mTweetTableQueries.getTweetById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.Default)
    }

    suspend fun createOrUpdate(tweetDTO: TweetDTO) {
        getTweetById(tweetDTO.mId).collectLatest { tweet ->
            tweet?.let {
                updateTweet(tweetDTO.mId, tweetDTO.mMessage)
            } ?: kotlin.run {
                createTweet(tweetDTO.mMessage)
            }
        }
    }

    suspend fun createTweet(message: String) {
        mTweetTableQueries.setTweetByValues(
            null,
            message,
            mTimeUtils.mCurrentMillis
        )
    }

    suspend fun updateTweet(id: Long, message: String) {
        mTweetTableQueries.updateTweetById(
            message,
            mTimeUtils.mCurrentMillis,
            id
        )
    }

    suspend fun deleteTweetById(id: Long) {
        mTweetTableQueries.deleteTweetById(id)
    }

    suspend fun deleteAllTweets() {
        mTweetTableQueries.deleteAllTweets()
    }

}