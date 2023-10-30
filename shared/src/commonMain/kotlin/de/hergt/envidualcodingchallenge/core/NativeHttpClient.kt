package de.hergt.envidualcodingchallenge.core

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.http.URLProtocol
import io.ktor.http.path

expect class NativeHttpClient() {

    val mClient: HttpClient

}

fun DefaultRequest.DefaultRequestBuilder.commonDefault() {
    url {
        protocol = URLProtocol.HTTP
        host = "192.168.178.46"
        port = 8080
        path("/api/v1")
    }
}