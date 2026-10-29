package com.alifwanoon.v72.data

import androidx.room.*

/**
 * 12 جدول للنظام الكامل
 */

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val token: String = "ADMIN-ELITE-2024",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "codes")
data class CodeEntity(
    @PrimaryKey val code: String,
    val pairedWith: String?,
    val isUsed: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val expiresAt: Long = System.currentTimeMillis() + 600_000
)

@Entity(tableName = "sessions")
data class SessionEntity(
    @PrimaryKey val sessionId: String,
    val userId: String,
    val startTime: Long,
    val tickCount: Int = 0,
    val status: String = "active"
)

@Entity(tableName = "alerts")
data class AlertEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String,
    val tone: Int, // 523..1318
    val vibratePattern: String,
    val timestamp: Long
)

@Entity(tableName = "tones")
data class ToneEntity(
    @PrimaryKey val frequency: Int,
    val name: String,
    val durationMs: Int = 300,
    val isFavorite: Boolean = false
)

@Entity(tableName = "limits")
data class LimitEntity(
    @PrimaryKey val dayKey: String,
    val count: Int = 0,
    val max: Int = 2
)

@Entity(tableName = "cooldowns")
data class CooldownEntity(
    @PrimaryKey val userId: String,
    val lastAction: Long,
    val cooldownMinutes: Int = 10
)

@Entity(tableName = "ticks")
data class TickEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: String,
    val tickNumber: Int,
    val intervalSeconds: Long = 30,
    val executedAt: Long
)

@Entity(tableName = "paired_history")
data class PairedHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val leftCode: String,
    val rightCode: String,
    val fullCode: String,
    val success: Boolean,
    val timestamp: Long
)

@Entity(tableName = "settings")
data class SettingEntity(
    @PrimaryKey val key: String,
    val value: String,
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "logs")
data class LogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val level: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "backups")
data class BackupEntity(
    @PrimaryKey val backupId: String,
    val path: String,
    val sizeBytes: Long,
    val createdAt: Long
)

@Dao
interface AlifDao {
    @Query("SELECT * FROM codes WHERE isUsed = 0 LIMIT :limit")
    suspend fun getUnusedCodes(limit: Int = 2): List<CodeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCode(code: CodeEntity)

    @Query("SELECT * FROM limits WHERE dayKey = :key")
    suspend fun getLimit(key: String): LimitEntity?

    @Query("SELECT COUNT(*) FROM ticks WHERE sessionId = :sid")
    suspend fun countTicks(sid: String): Int
}

@Database(
    entities = [
        UserEntity::class, CodeEntity::class, SessionEntity::class,
        AlertEntity::class, ToneEntity::class, LimitEntity::class,
        CooldownEntity::class, TickEntity::class, PairedHistoryEntity::class,
        SettingEntity::class, LogEntity::class, BackupEntity::class
    ],
    version = 7,
    exportSchema = false
)
abstract class AlifDatabase : RoomDatabase() {
    abstract fun alifDao(): AlifDao
}
