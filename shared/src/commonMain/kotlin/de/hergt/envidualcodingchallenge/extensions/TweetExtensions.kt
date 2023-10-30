package de.hergt.envidualcodingchallenge.extensions

import de.hergt.envidualcodingchallenge.Tweet
import de.hergt.envidualcodingchallenge.dtos.TweetDTO

fun Tweet.toDTO(): TweetDTO {
    return TweetDTO(
        mId = mId,
        mMessage = mMessage,
        mTimestamp = mTimestamp
    )
}