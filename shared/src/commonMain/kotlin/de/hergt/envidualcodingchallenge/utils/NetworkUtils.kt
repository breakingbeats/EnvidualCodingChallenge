package de.hergt.envidualcodingchallenge.utils

object NetworkUtils {

    private val availableApiVersions = listOf(1)
    private val mLatestApiVersion = availableApiVersions.max()

    val mLatestApiPath = "/api/v$mLatestApiVersion"

}