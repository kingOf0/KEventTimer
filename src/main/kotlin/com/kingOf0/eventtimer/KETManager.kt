package com.kingOf0.eventtimer

import com.kingOf0.eventtimer.file.ConfigFile

object KETManager {

    private const val DAY = 86_400_000L
    private const val HOUR = 3600000L
    private const val MINUTE = 60000L
    private const val SECOND = 1000L

    private lateinit var config: ConfigFile
    var commands = ArrayList<String>()

    var eventMillis: Long = 72_000_000L

    fun loadConfig(configFile: ConfigFile) {
        val split = configFile.getString("event").split(".")
        eventMillis = split[0].toInt() * HOUR + split[1].toInt() * MINUTE
    }

    fun remainingTime(): Long {
        val time = System.currentTimeMillis() % DAY

        return if (time > eventMillis) {
            DAY * 2 - time
        } else {
            eventMillis - time
        }
    }

    fun formatMilliSecond(l: Long): String {
        var millis = l
        val days: Long = millis / DAY
        millis -= days * DAY
        val hours: Long = millis / HOUR
        millis -= hours * HOUR
        val minutes: Long = millis / MINUTE
        millis -= minutes * MINUTE
        val seconds: Long = millis / SECOND
        millis -= seconds * SECOND
        val builder = StringBuilder()
        if (days > 0) {
            builder.append(days).append(" ").append(config.getString("day"))
        }
        if (hours > 0) {
            builder.append(" ").append(hours).append(" ").append(config.getString("hour"))
        }
        if (minutes > 0) {
            builder.append(" ").append(minutes).append(" ").append(config.getString("minute"))
        }
        if (seconds > 0) {
            builder.append(" ").append(seconds).append(" ").append(config.getString("second"))
        }
        return builder.toString()
    }

}