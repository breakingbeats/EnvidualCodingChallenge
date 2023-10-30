package de.hergt.envidualcodingchallenge.routing.api.v1.tweet

import de.hergt.envidualcodingchallenge.core.constants.FormData
import de.hergt.envidualcodingchallenge.core.MainController
import de.hergt.envidualcodingchallenge.extensions.toDTO
import de.hergt.envidualcodingchallenge.repositories.TweetRepository
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.server.application.call
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import kotlinx.coroutines.flow.collectLatest

fun Route.tweetApi(mainController: MainController) {

    val repository = TweetRepository(mainController.mDriverFactory)

    route(repository.apiPath) {
        get {
            call.request.queryParameters["keyword"]?.let { keyword ->
                repository.getTweetsByKeyword(keyword).collectLatest { tweets ->
                    call.respond(tweets.map { it.toDTO() })
                }
            } ?: kotlin.run {
                repository.getAllTweets().collectLatest { tweets ->
                    call.respond(tweets.map { it.toDTO() })
                }
            }
        }

        get("/{id}") {
            call.parameters["id"]?.toLongOrNull()?.let { tweetId ->
                repository.getTweetById(tweetId).collectLatest { tweet ->
                    tweet?.let {
                        call.respond(it.toDTO())
                    } ?: call.respond(HttpStatusCode.BadRequest)
                }
            } ?: call.respond(HttpStatusCode.BadRequest)
        }

        post {
            var mTweetId: Long? = null
            var mTweetMessage: String? = null

            val multipartData = call.receiveMultipart()

            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        when (part.name) {
                            FormData.mTweetIdName -> {
                                mTweetId = part.value.toLongOrNull()
                            }
                            FormData.mTweetMessageName -> {
                                mTweetMessage = part.value
                            }
                        }
                    }
                    else -> {}
                }
                part.dispose()
            }

            mTweetMessage?.let { message ->
                mTweetId?.let { id ->
                    repository.updateTweet(id, message)
                } ?: kotlin.run {
                    repository.createTweet(message)
                }
                call.respond(HttpStatusCode.OK)
            } ?: call.respond(HttpStatusCode.BadRequest)
        }
    }
}