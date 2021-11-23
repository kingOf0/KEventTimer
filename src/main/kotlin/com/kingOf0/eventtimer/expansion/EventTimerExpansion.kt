package com.kingOf0.eventtimer.expansion

import com.kingOf0.eventtimer.KETManager.events
import com.kingOf0.eventtimer.KETManager.formatMilliSecond
import com.kingOf0.eventtimer.KETManager.remainingTime
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class EventTimerExpansion(plugin: JavaPlugin) : PlaceholderExpansion() {

    private var messages = HashMap<String, String>()

    init {
        object : BukkitRunnable() {
            override fun run() {
                for (event in events) {
                    messages[event.key] = formatMilliSecond(remainingTime(event.value))
                }
            }
        }.runTaskTimerAsynchronously(plugin, 20, 20)
    }

    override fun getIdentifier(): String {
        return "keventtimer"
    }

    override fun persist(): Boolean {
        return true
    }

    override fun getAuthor(): String {
        return "kingOf0"
    }

    override fun getVersion(): String {
        return "1.0.0"
    }

    override fun onRequest(player: OfflinePlayer?, params: String): String {
        return foo(params)
    }

    override fun onPlaceholderRequest(player: Player?, params: String): String {
        return foo(params)
    }

    private fun foo(params: String): String {
        val split = params.split("_")
        if (split.size != 2) return ""
        if (split[0] == "remaining") {
            return messages.getOrDefault(split[1], "")
        }
        return ""
    }
}