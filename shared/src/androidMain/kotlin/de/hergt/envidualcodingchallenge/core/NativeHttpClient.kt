package de.hergt.envidualcodingchallenge.core

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.http.path
import java.util.concurrent.TimeUnit

actual class NativeHttpClient {

    actual val mClient = HttpClient(CIO) {
        defaultRequest {
            commonDefault()
        }
    }

}