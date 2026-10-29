package com.alifwanoon.v72.core

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.security.SecureRandom
import kotlin.collections.HashSet

/**
 * مولد الأكواد المقترنة مع منع التكرار
 */
class CodeGen {
    private val seen: MutableSet<String> = HashSet(K.Limits.MAX_SEEN_SIZE)
    private val mutex = Mutex()
    private val random = SecureRandom()
    
    private val chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789".toCharArray()

    suspend fun generatePaired(): String = mutex.withLock {
        var attempts = 0
        while (attempts < K.Limits.MAX_CODE_ATTEMPTS) {
            val left = generateSegment(4)
            val right = generateSegment(4)
            val paired = "$left-$right"
            
            if (paired !in seen) {
                seen.add(paired)
                // إدارة الذاكرة: إزالة الأقدم عند الامتلاء
                if (seen.size > K.Limits.MAX_SEEN_SIZE) {
                    seen.remove(seen.first())
                }
                return paired
            }
            attempts++
        }
        throw IllegalStateException("Failed to generate unique code after ${K.Limits.MAX_CODE_ATTEMPTS}")
    }

    private fun generateSegment(length: Int): String {
        return (0 until length)
            .map { chars[random.nextInt(chars.size)] }
            .joinToString("")
    }

    fun isSeen(code: String): Boolean = code in seen

    fun pairedCodeLogic(a: String, b: String): Boolean {
        if (a.length != 4 || b.length != 4) return false
        // منطق الاقتران: مجموع قيم الأحرف يجب أن يكون زوجي
        val sum = (a + b).sumOf { it.code }
        return sum % 2 == 0
    }

    suspend fun clearSeen() = mutex.withLock { seen.clear() }
}
