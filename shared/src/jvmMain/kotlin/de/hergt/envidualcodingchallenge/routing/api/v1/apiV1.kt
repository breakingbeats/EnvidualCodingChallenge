package de.hergt.envidualcodingchallenge.routing.api.v1

import de.hergt.envidualcodingchallenge.core.MainController
import de.hergt.envidualcodingchallenge.routing.api.v1.tweet.tweetApi
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.apiV1(mainController: MainController) {
    route("/api/v1") {
        tweetApi(mainController)
    }
}