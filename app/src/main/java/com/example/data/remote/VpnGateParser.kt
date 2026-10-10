package com.example.data.remote

import android.util.Log
import com.example.data.model.SstpServer

object VpnGateParser {
    private const val TAG = "VpnGateParser"

    fun parse(csvContent: String): List<SstpServer> {
        val servers = mutableListOf<SstpServer>()
        val lines = csvContent.lines()

        var headerFound = false
        var colHostName = 0
        var colIp = 1
        var colScore = 2
        var colPing = 3
        var colSpeed = 4
        var colCountryLong = 5
        var colCountryShort = 6
        var colSessions = 7
        var colUptime = 8
        var colUsers = 9
        var colOperator = 12
        var colMessage = 13

        for (rawLine in lines) {
            val line = rawLine.trim()
            if (line.isEmpty() || line == "*") continue

            if (line.startsWith("#") || line.startsWith("HostName,", ignoreCase = true)) {
                // Header row
                val headers = parseCsvRow(line.removePrefix("#"))
                for ((index, header) in headers.withIndex()) {
                    when (header.trim().lowercase()) {
                        "hostname" -> colHostName = index
                        "ip" -> colIp = index
                        "score" -> colScore = index
                        "ping" -> colPing = index
                        "speed" -> colSpeed = index
                        "countrylong" -> colCountryLong = index
                        "countryshort" -> colCountryShort = index
                        "numvpnsessions" -> colSessions = index
                        "uptime" -> colUptime = index
                        "totalusers" -> colUsers = index
                        "operator" -> colOperator = index
                        "message" -> colMessage = index
                    }
                }
                headerFound = true
                continue
            }

            if (!headerFound) {
                // If header line hasn't been encountered yet and it's meta
                if (line.startsWith("*vpn_servers")) continue
            }

            try {
                val row = parseCsvRow(line)
                if (row.size <= maxOf(colHostName, colIp, colCountryShort)) {
                    continue
                }

                val hostname = row.getOrNull(colHostName)?.trim().orEmpty()
                val ip = row.getOrNull(colIp)?.trim().orEmpty()

                if (hostname.isBlank() && ip.isBlank()) continue

                val score = row.getOrNull(colScore)?.trim()?.toLongOrNull() ?: 0L
                val ping = row.getOrNull(colPing)?.trim()?.toLongOrNull() ?: 999L
                val speed = row.getOrNull(colSpeed)?.trim()?.toLongOrNull() ?: 0L
                val countryLong = row.getOrNull(colCountryLong)?.trim() ?: "Unknown"
                val countryShort = row.getOrNull(colCountryShort)?.trim() ?: "UN"
                val sessions = row.getOrNull(colSessions)?.trim()?.toIntOrNull() ?: 0
                val uptime = row.getOrNull(colUptime)?.trim()?.toLongOrNull() ?: 0L
                val users = row.getOrNull(colUsers)?.trim()?.toLongOrNull() ?: 0L
                val operator = row.getOrNull(colOperator)?.trim() ?: "VPN Gate"
                val message = row.getOrNull(colMessage)?.trim() ?: ""

                val id = if (hostname.isNotBlank()) hostname else ip

                servers.add(
                    SstpServer(
                        id = id,
                        hostname = hostname,
                        ip = ip,
                        port = 443, // Standard SSTP Port for SoftEther VPN Gate servers
                        score = score,
                        pingMs = ping,
                        speedBps = speed,
                        countryLong = countryLong,
                        countryShort = countryShort,
                        numVpnSessions = sessions,
                        uptimeSeconds = uptime,
                        totalUsers = users,
                        operator = operator,
                        message = message,
                        lastFetchedAt = System.currentTimeMillis()
                    )
                )
            } catch (e: Exception) {
                Log.w(TAG, "Failed to parse row: $line", e)
            }
        }

        return servers
    }

    private fun parseCsvRow(line: String): List<String> {
        val tokens = mutableListOf<String>()
        val sb = StringBuilder()
        var insideQuotes = false

        for (c in line) {
            when {
                c == '\"' -> {
                    insideQuotes = !insideQuotes
                }
                c == ',' && !insideQuotes -> {
                    tokens.add(sb.toString())
                    sb.setLength(0)
                }
                else -> {
                    sb.append(c)
                }
            }
        }
        tokens.add(sb.toString())
        return tokens
    }
}
