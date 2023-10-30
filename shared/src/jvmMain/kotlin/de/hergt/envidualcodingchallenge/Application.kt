package de.hergt.envidualcodingchallenge

import de.hergt.envidualcodingchallenge.core.MainController
import de.hergt.envidualcodingchallenge.routing.api.v1.apiV1
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing

fun main() {
    embeddedServer(Netty, port = 8080) {

        install(ContentNegotiation)

        val mainController = MainController()

        routing {
            apiV1(mainController)
        }

    }.start(true)
}