package de.hergt.envidualcodingchallenge.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TweetDTO(
    @SerialName("id")
    val mId: Long,

    @SerialName("message")
    val mMessage: String,

    @SerialName("timestamp")
    val mTimestamp: Long
)
