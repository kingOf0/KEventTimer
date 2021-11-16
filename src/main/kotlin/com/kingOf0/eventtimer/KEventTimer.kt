package com.kingOf0.eventtimer

import com.kingOf0.eventtimer.KETManager.commands
import com.kingOf0.eventtimer.KETManager.loadConfig
import com.kingOf0.eventtimer.KETManager.remainingTime
import com.kingOf0.eventtimer.expansion.EventTimerExpansion
import com.kingOf0.eventtimer.file.ConfigFile
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.logging.Logger

lateinit var LOGGER: Logger
class KEventTimer : JavaPlugin() {

    override fun onLoad() {
        LOGGER = logger
    }

    override fun onEnable() {
        val configFile = ConfigFile("config", this)
        commands += configFile.getStringList("commands")

        object : BukkitRunnable() {
            override fun run() {
                for (command in commands) {
                    server.dispatchCommand(server.consoleSender, command)
                }
            }
        }.runTaskLater(this, remainingTime() / 50)

        loadConfig(configFile)

        if (server.pluginManager.isPluginEnabled("PlaceholderAPI")) {
            logger.info("+ Papi expansion registered")
            EventTimerExpansion(this).register()
        } else {
            logger.info("- Papi expansion couldn't registered")
        }
    }


}