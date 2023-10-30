package de.hergt.envidualcodingchallenge.utils

import de.hergt.envidualcodingchallenge.extensions.toTwoDigits
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TimeUtils {

    private val mInstant = Clock.System.now()

    val mCurrentMillis: Long
        get() = mInstant.epochSeconds

    private fun getLocalDateTimeFromMillis(timeMillis: Long?): LocalDateTime {
        return Instant.fromEpochMilliseconds(timeMillis ?: 0).toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun convertTimeMillisToDateString(timeMillis: Long?): String {
        val dateTime = getLocalDateTimeFromMillis(timeMillis)
        return "${dateTime.dayOfMonth.toTwoDigits()}.${dateTime.monthNumber.toTwoDigits()}.${dateTime.year}"
    }

}