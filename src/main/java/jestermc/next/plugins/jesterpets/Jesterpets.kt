@file:Suppress("SpellCheckingInspection")

package jestermc.next.plugins.jesterpets

import JsonFile.JsonFile
import jestermc.next.plugins.jesterpets.utils.Logger
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import kotlin.properties.Delegates


class Jesterpets : JavaPlugin() {
    companion object {
        var logger: Logger by Delegates.notNull()
        var plugin by Delegates.notNull<Jesterpets>()
    }

    override fun onEnable() {
        // Plugin startup logic
        plugin = this
        Jesterpets.logger = Logger(this)

        val logger = Jesterpets.logger

        logger.info("============= JESTERPETS =============")
        logger.info("   Loaded ${this.javaClass.simpleName} ")
        logger.info("   Version: 1.0-SNAPSHOT   ")
        logger.info("   Author: NextDev ")
        logger.info("")
        logger.info("   Loading json config file...")
        val config = JsonFile(dataFolder.path,"config")
        if(!config.exists()) {
            logger.info("   Creating config file...")
            config.create()
            val defaults = hashMapOf<String, Any?>(
                "version" to "1.0-SNAPSHOT",
            )
            config.putAll(defaults)
            config.save()
        }
        config.reload()

        logger.info("   Loading data folder...")
        val dataFolder = File(dataFolder.path+"${System.getProperty("file.separator")}data")
        if(!dataFolder.exists()) {
            logger.info("   Creating data folder...")
            dataFolder.mkdirs()
        }
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}