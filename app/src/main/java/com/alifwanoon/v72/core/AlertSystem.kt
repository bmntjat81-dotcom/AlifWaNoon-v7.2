package com.alifwanoon.v72.core

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.delay

/**
 * نظام التنبيه بالاهتزاز والنغمات
 */
class AlertSystem(private val context: Context) {
    
    private val vibrator: Vibrator by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vm.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    private val toneGen by lazy { ToneGenerator(AudioManager.STREAM_NOTIFICATION, 80) }

    // أنماط الاهتزاز المختلفة
    val patterns = mapOf(
        "success" to longArrayOf(0, K.Alerts.VIBRATE_SHORT, K.Alerts.PATTERN_GAP, K.Alerts.VIBRATE_SHORT),
        "warning" to longArrayOf(0, K.Alerts.VIBRATE_LONG, K.Alerts.PATTERN_GAP, K.Alerts.VIBRATE_SHORT),
        "paired" to longArrayOf(0, 100, 100, 100, 100, 400),
        "error" to longArrayOf(0, 500, 200, 500),
        "tick" to longArrayOf(0, 50)
    )

    fun vibrate(patternName: String = "success") {
        val pattern = patterns[patternName] ?: patterns["success"]!!
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(pattern, -1)
        }
    }

    suspend fun triggerWithTone(code: String) {
        vibrate("paired")
        
        // تشغيل نغمة بناءً على الكود
        val toneIndex = code.hashCode().mod(K.TONES.size)
        val freq = K.toneForIndex(toneIndex)
        playToneForFrequency(freq)
        
        delay(K.TICK_MILLIS)
    }

    private fun playToneForFrequency(freq: Int) {
        // ربط التردد بنغمة ToneGenerator
        val toneType = when {
            freq < 400 -> ToneGenerator.TONE_CDMA_PIP
            freq < 600 -> ToneGenerator.TONE_CDMA_DIAL_TONE_LITE
            freq < 900 -> ToneGenerator.TONE_PROP_ACK
            else -> ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD
        }
        try {
            toneGen.startTone(toneType, 300)
        } catch (_: Exception) {}
    }

    fun stop() {
        vibrator.cancel()
        toneGen.release()
    }
}
