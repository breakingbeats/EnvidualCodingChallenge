package de.hergt.envidualcodingchallenge.core

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO

actual class NativeHttpClient {

    actual val mClient = HttpClient(CIO) {  }

}