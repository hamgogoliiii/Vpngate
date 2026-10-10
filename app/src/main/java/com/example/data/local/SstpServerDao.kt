package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.SstpServer
import kotlinx.coroutines.flow.Flow

@Dao
interface SstpServerDao {

    @Query("SELECT * FROM sstp_servers ORDER BY score DESC")
    fun getAllServersFlow(): Flow<List<SstpServer>>

    @Query("SELECT * FROM sstp_servers WHERE isFavorite = 1 ORDER BY score DESC")
    fun getFavoriteServersFlow(): Flow<List<SstpServer>>

    @Query("SELECT * FROM sstp_servers WHERE id = :id LIMIT 1")
    suspend fun getServerById(id: String): SstpServer?

    @Query("SELECT COUNT(*) FROM sstp_servers")
    fun getServerCountFlow(): Flow<Int>

    @Query("SELECT COUNT(*) FROM sstp_servers")
    suspend fun getServerCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServers(servers: List<SstpServer>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServer(server: SstpServer)

    @Update
    suspend fun updateServer(server: SstpServer)

    @Query("UPDATE sstp_servers SET measuredPingMs = :pingMs, lastTestedAt = :testedAt WHERE id = :id")
    suspend fun updateMeasuredPing(id: String, pingMs: Long, testedAt: Long)

    @Query("UPDATE sstp_servers SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: String, isFavorite: Boolean)

    @Query("UPDATE sstp_servers SET isUsed = :isUsed WHERE id = :id")
    suspend fun updateUsed(id: String, isUsed: Boolean)

    @Query("SELECT isFavorite FROM sstp_servers WHERE id = :id LIMIT 1")
    suspend fun isFavorite(id: String): Boolean?

    @Query("DELETE FROM sstp_servers WHERE isFavorite = 0")
    suspend fun deleteNonFavorites()

    @Query("DELETE FROM sstp_servers")
    suspend fun deleteAll()
}
