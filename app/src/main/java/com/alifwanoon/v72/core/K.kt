package com.alifwanoon.v72.core

/**
 * ثوابت النظام الأساسية - AlifWaNoon v7.2
 * لا تغير القيم إلا بعد فهم تأثيرها على التزامن
 */
object K {
    const val ADMIN_TOKEN = "ADMIN-ELITE-2024"
    const val DAILY_LIMIT = 2
    const val COOLDOWN_MINUTES = 10
    const val TICK_SECONDS = 30L
    const val TICK_MILLIS = TICK_SECONDS * 1000L

    // نغمات النظام الأساسية - ترددات موسيقية
    val TONES = intArrayOf(
        523,  // C5 - Do
        659,  // E5 - Mi
        784,  // G5 - Sol
        880,  // A5 - La
        1046, // C6 - Do عالي
        1318, // E6 - Mi عالي
        440,  // A4 - La أساسي
        349,  // F4 - Fa
        262   // C4 - Do منخفض
    )

    object Alerts {
        const val VIBRATE_SHORT = 200L
        const val VIBRATE_LONG = 600L
        const val PATTERN_GAP = 150L
    }

    object Limits {
        const val MAX_SEEN_SIZE = 5000
        const val MAX_CODE_ATTEMPTS = 100
    }

    object Db {
        const val NAME = "alifwanoon_v72.db"
        const val VERSION = 7
    }

    fun toneForIndex(index: Int): Int = TONES[index % TONES.size]
}
