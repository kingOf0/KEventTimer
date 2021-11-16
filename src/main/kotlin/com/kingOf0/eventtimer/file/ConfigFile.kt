@file:Suppress("MemberVisibilityCanBePrivate")

package com.kingOf0.eventtimer.file

import com.kingOf0.eventtimer.LOGGER
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.logging.Level

class ConfigFile(private var fileName: String, private val plugin: JavaPlugin) : YamlConfiguration() {
    private val configFile: File
    init {
        if(!fileName.endsWith(".yml")) fileName += ".yml"

        configFile = File(plugin.dataFolder, fileName)

        if(!configFile.exists()) {
            configFile.parentFile.mkdirs()
            this.saveDefault()
        }
        this.load()
    }

    fun saveDefault() {
        plugin.saveResource(fileName, false)
    }

    fun load(){
        try {
            super.load(configFile)
        } catch (e: Exception) {
            LOGGER.log(Level.WARNING, "", e)
        }
    }

    fun save() {
        try {
            super.save(configFile)
        } catch (e: Exception) {
            LOGGER.log(Level.WARNING, "", e)
        }
    }
}