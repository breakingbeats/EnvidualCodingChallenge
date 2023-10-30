package de.hergt.envidualcodingchallenge.core

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint

actual class NativeHttpClient {

    actual val mClient = HttpClient(CIO) {
        engine {
            endpoint {
                connectTimeout = 30000
            }
        }
    }

}