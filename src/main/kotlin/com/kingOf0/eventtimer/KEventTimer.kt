package com.kingOf0.eventtimer

import com.kingOf0.eventtimer.KETManager.configFile
import com.kingOf0.eventtimer.KETManager.loadConfig
import com.kingOf0.eventtimer.expansion.EventTimerExpansion
import com.kingOf0.eventtimer.file.ConfigFile
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

lateinit var LOGGER: Logger
class KEventTimer : JavaPlugin() {

    override fun onLoad() {
        LOGGER = logger
    }

    override fun onEnable() {
        configFile = ConfigFile("config", this)

        if (server.pluginManager.isPluginEnabled("PlaceholderAPI")) {
            logger.info("+ PAPI | Hooked successfully!")
            EventTimerExpansion(this).register()
        } else {
            logger.info("- PAPI | Couldn't hooked!")
        }

        loadConfig(configFile, this)
    }


}