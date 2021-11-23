package com.kingOf0.eventtimer

import com.kingOf0.eventtimer.expansion.EventTimerExpansion
import com.kingOf0.eventtimer.file.ConfigFile
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object KETManager {

    private const val DAY = 86_400_000L
    private const val HOUR = 3600000L
    private const val MINUTE = 60000L
    private const val SECOND = 1000L

    lateinit var configFile: ConfigFile

    //               18.00 -> 64 800 000
    var events = HashMap<String, Long>()

    private var OFFSET = HOUR * 3

    fun loadConfig(configFile: ConfigFile, javaPlugin: JavaPlugin) {
        if (configFile.isConfigurationSection("commands")) {
            val configurationSection = configFile.getConfigurationSection("commands")
            for (key in configurationSection.getKeys(false)) {
                val split = key.split(":")
                val eventMillis = split[0].toInt() * HOUR + split[1].toInt() * MINUTE
                val commands = configurationSection.getStringList(key)

                events[key] = eventMillis

                object : BukkitRunnable() {
                    override fun run() {
                        for (command in commands) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command)
                        }
                    }
                }.runTaskLater(javaPlugin, remainingTime(eventMillis) / 50)
            }
        }
        OFFSET = configFile.getInt("hourOffset", 3) * HOUR
    }

    fun remainingTime(millis: Long): Long {
        val time = (System.currentTimeMillis() % DAY) + OFFSET
        return if (time > millis) {
            DAY * 2 - time
        } else {
            millis - time
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
            builder.append(days).append(" ").append(configFile.getString("day"))
        }
        if (hours > 0) {
            builder.append(" ").append(hours).append(" ").append(configFile.getString("hour"))
        }
        if (minutes > 0) {
            builder.append(" ").append(minutes).append(" ").append(configFile.getString("minute"))
        }
        if (seconds > 0) {
            builder.append(" ").append(seconds).append(" ").append(configFile.getString("second"))
        }
        return builder.toString()
    }

}