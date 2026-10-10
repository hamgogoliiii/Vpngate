package com.example.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.InetSocketAddress
import java.net.Socket

object PingTester {
    suspend fun testLatency(targetHost: String, port: Int = 443, timeoutMs: Int = 2500): Long? =
        withContext(Dispatchers.IO) {
            val startTime = System.currentTimeMillis()
            var socket: Socket? = null
            try {
                socket = Socket()
                socket.connect(InetSocketAddress(targetHost, port), timeoutMs)
                val latency = System.currentTimeMillis() - startTime
                latency
            } catch (e: Exception) {
                // If port 443 is filtered or blocked, try port 80 as a fallback ping measure
                if (port == 443) {
                    try {
                        val fallbackStart = System.currentTimeMillis()
                        val fallbackSocket = Socket()
                        fallbackSocket.connect(InetSocketAddress(targetHost, 80), timeoutMs)
                        val fallbackLatency = System.currentTimeMillis() - fallbackStart
                        fallbackSocket.close()
                        return@withContext fallbackLatency
                    } catch (_: Exception) {
                        null
                    }
                } else {
                    null
                }
            } finally {
                try {
                    socket?.close()
                } catch (_: Exception) {}
            }
        }
}
