package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.data.local.SstpServerDao
import com.example.data.model.InitialSeedData
import com.example.data.model.SstpServer
import com.example.data.remote.PingTester
import com.example.data.remote.VpnGateApi
import com.example.data.remote.VpnGateParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class SstpRepository(
    private val dao: SstpServerDao,
    private val api: VpnGateApi,
    private val context: Context
) {
    private val prefs = context.getSharedPreferences("vpn_gate_prefs", Context.MODE_PRIVATE)

    private val _lastSyncTimestamp = MutableStateFlow(
        prefs.getLong(KEY_LAST_SYNC, System.currentTimeMillis() - 3600_000L)
    )
    val lastSyncTimestamp = _lastSyncTimestamp.asStateFlow()

    val allServers: Flow<List<SstpServer>> = dao.getAllServersFlow()
    val favoriteServers: Flow<List<SstpServer>> = dao.getFavoriteServersFlow()
    val serverCount: Flow<Int> = dao.getServerCountFlow()

    suspend fun initializeDatabaseIfNeeded() = withContext(Dispatchers.IO) {
        val count = dao.getServerCount()
        if (count == 0) {
            dao.insertServers(InitialSeedData.defaultServers)
        }
    }

    suspend fun refreshServers(): Result<Int> = withContext(Dispatchers.IO) {
        var lastError: Exception? = null

        for (url in VpnGateApi.MIRROR_URLS) {
            try {
                Log.d(TAG, "Attempting to fetch VPN Gate servers from: $url")
                val response = api.getVpnServersCsv(url)
                if (response.isSuccessful) {
                    val bodyString = response.body()?.string()
                    if (!bodyString.isNullOrBlank() && bodyString.contains("HostName", ignoreCase = true)) {
                        val parsedServers = VpnGateParser.parse(bodyString)
                        if (parsedServers.isNotEmpty()) {
                            // Merge with existing favorites and live ping measurements
                            val updatedServers = parsedServers.map { server ->
                                val existing = dao.getServerById(server.id)
                                if (existing != null) {
                                    server.copy(
                                        isFavorite = existing.isFavorite,
                                        measuredPingMs = existing.measuredPingMs,
                                        lastTestedAt = existing.lastTestedAt
                                    )
                                } else {
                                    server
                                }
                            }

                            dao.insertServers(updatedServers)
                            val now = System.currentTimeMillis()
                            prefs.edit().putLong(KEY_LAST_SYNC, now).apply()
                            _lastSyncTimestamp.value = now
                            Log.i(TAG, "Successfully refreshed ${updatedServers.size} servers from $url")
                            return@withContext Result.success(updatedServers.size)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.w(TAG, "Failed mirror $url: ${e.message}")
                lastError = e
            }
        }

        // If network fetch failed, ensure initial seed is at least present
        initializeDatabaseIfNeeded()
        return@withContext Result.failure(lastError ?: Exception("Unable to reach VPN Gate servers"))
    }

    suspend fun testServerPing(serverId: String): Long? = withContext(Dispatchers.IO) {
        val server = dao.getServerById(serverId) ?: return@withContext null
        val target = server.displayAddress
        val latency = PingTester.testLatency(target, server.port)
        if (latency != null) {
            dao.updateMeasuredPing(serverId, latency, System.currentTimeMillis())
        }
        latency
    }

    suspend fun toggleFavorite(serverId: String, isFavorite: Boolean) = withContext(Dispatchers.IO) {
        dao.updateFavorite(serverId, isFavorite)
    }

    suspend fun markServerUsed(serverId: String, isUsed: Boolean) = withContext(Dispatchers.IO) {
        dao.updateUsed(serverId, isUsed)
    }

    suspend fun resetToSeedData() = withContext(Dispatchers.IO) {
        dao.deleteAll()
        dao.insertServers(InitialSeedData.defaultServers)
    }

    companion object {
        private const val TAG = "SstpRepository"
        private const val KEY_LAST_SYNC = "key_last_sync_time"
    }
}
