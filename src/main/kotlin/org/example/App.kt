package org.example

import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.nio.charset.StandardCharsets

internal fun messagePayload(): String =
    """{"service":"kotlin1-service-3","message":"Hello from Kotlin1 service 3","sharedLibrary":{"name":"kot-lib-3","version":"${Kotlib3.version()}"}}"""

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8083
    val server = HttpServer.create(InetSocketAddress("0.0.0.0", port), 0)

    server.createContext("/health") { exchange ->
        val body = """{"status":"ok","service":"kotlin1-service-3"}"""
            .toByteArray(StandardCharsets.UTF_8)
        exchange.responseHeaders.add("Content-Type", "application/json; charset=utf-8")
        exchange.sendResponseHeaders(200, body.size.toLong())
        exchange.responseBody.use { it.write(body) }
    }

    server.createContext("/api/message") { exchange ->
        val body = messagePayload().toByteArray(StandardCharsets.UTF_8)
        exchange.responseHeaders.add("Content-Type", "application/json; charset=utf-8")
        exchange.sendResponseHeaders(200, body.size.toLong())
        exchange.responseBody.use { it.write(body) }
    }

    server.start()
    println("kotlin1-service-3 listening on http://localhost:$port")
}
