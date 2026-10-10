package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sstp_servers")
data class SstpServer(
    @PrimaryKey
    val id: String, // Hostname or IP
    val hostname: String,
    val ip: String,
    val port: Int = 443,
    val score: Long,
    val pingMs: Long,
    val speedBps: Long,
    val countryLong: String,
    val countryShort: String,
    val numVpnSessions: Int,
    val uptimeSeconds: Long,
    val totalUsers: Long,
    val operator: String,
    val message: String,
    val measuredPingMs: Long? = null,
    val lastTestedAt: Long? = null,
    val lastFetchedAt: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false,
    val isVip: Boolean = false,
    val isUsed: Boolean = false
) {
    val speedMbps: Double
        get() = (speedBps * 8.0) / 1_000_000.0

    val formattedSpeed: String
        get() {
            val mbps = speedMbps
            return if (mbps >= 1.0) {
                String.format("%.1f Mbps", mbps)
            } else {
                val kbps = (speedBps * 8.0) / 1_000.0
                String.format("%.0f Kbps", kbps)
            }
        }

    val formattedUptime: String
        get() {
            val days = uptimeSeconds / 86400
            val hours = (uptimeSeconds % 86400) / 3600
            return when {
                days > 0 -> "${days}d ${hours}h"
                hours > 0 -> "${hours}h"
                else -> "${uptimeSeconds / 60}m"
            }
        }

    val countryFlag: String
        get() {
            if (countryShort.length != 2) return "🌐"
            val upper = countryShort.uppercase()
            val firstCodePoint = Character.codePointAt(upper, 0) - 0x41 + 0x1F1E6
            val secondCodePoint = Character.codePointAt(upper, 1) - 0x41 + 0x1F1E6
            return try {
                String(Character.toChars(firstCodePoint)) + String(Character.toChars(secondCodePoint))
            } catch (e: Exception) {
                "🌐"
            }
        }

    val farsiCountryName: String
        get() = when (countryShort.uppercase()) {
            "JP" -> "ژاپن"
            "KR" -> "کره جنوبی"
            "SG" -> "سنگاپور"
            "DE" -> "آلمان"
            "US" -> "ایالات متحده آمریکا"
            "GB", "UK" -> "بریتانیا"
            "FR" -> "فرانسه"
            "NL" -> "هلند"
            "CA" -> "کانادا"
            "AU" -> "استرالیا"
            else -> countryLong
        }

    val farsiRegionTitle: String
        get() = if (message.isNotBlank()) {
            "$farsiCountryName ($message)"
        } else {
            when (countryShort.uppercase()) {
                "JP" -> if (hostname.contains("92") || id.contains("92")) "ژاپن (Tokyo Relay #1)" else "ژاپن (تسوکوبا)"
                "KR" -> "کره جنوبی (سئول)"
                "SG" -> "سنگاپور (مرکز سرور ۱)"
                "DE" -> "آلمان (فرانکفورت)"
                "US" -> "آمریکا (اشبرن)"
                "GB" -> "بریتانیا (لندن)"
                "FR" -> "فرانسه (پاریس)"
                else -> "$farsiCountryName ($operator)"
            }
        }

    val cityOrRegion: String
        get() = if (message.isNotBlank()) message else operator

    val nodeCode: String
        get() {
            val hash = Math.abs(id.hashCode()) % 89 + 10
            return "${countryShort.uppercase()}-$hash"
        }

    val stabilityPercentage: Double
        get() {
            val uptimeDays = uptimeSeconds / 86400.0
            return (95.0 + (uptimeDays % 4.8)).coerceIn(92.0, 99.8)
        }

    val displayAddress: String
        get() = if (hostname.isNotBlank() && !hostname.contains(" ")) hostname else ip

    val displayAddressWithPort: String
        get() = "$displayAddress:$port"

    val fullProfileText: String
        get() = """
            SSTP VPN Server Profile
            -----------------------
            Host: $displayAddress
            IP: $ip
            Port: $port
            Username: vpn
            Password: vpn
            Country: $countryLong ($countryShort)
            Speed: $formattedSpeed
            Score: $score
        """.trimIndent()
}
