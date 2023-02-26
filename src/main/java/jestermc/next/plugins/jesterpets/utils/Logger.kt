package jestermc.next.plugins.jesterpets.utils

import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class Logger(val plugin: JavaPlugin) {
    private val log = mutableListOf<String>()
    private var traces = mutableListOf<Exception>()
    private var level = 0

    enum class Level(int: Int) {
        TRACE(4),
        DEBUG(0),
        INFO(1),
        WARN(2),
        ERROR(3),
        OFF(5)
    }

    fun info(message: String) {
        if(level >= Level.INFO.ordinal) {
            println("[JesterPets] [INFO]: $message")
            log.add(message)
        }
    }

    fun warn(message: String) {
        if(level >= Level.WARN.ordinal) {
            println("[JesterPets] [WARN]: $message")
            log.add(message)
        }
    }

    fun error(message: String) {
        if(level >= Level.ERROR.ordinal) {
            println("[JesterPets] [ERROR]: $message")
            log.add(message)
        }
    }

    fun trace(message: String, exception: Exception) {
        if(level >= Level.TRACE.ordinal) {
            println("[JesterPets] [TRACE]: $message")
            traces.add(exception)
            log.add(message)
        }
    }

    fun save() {
        val dataFolder = plugin.dataFolder.path+"${System.getProperty("file.separator")}logs"

        // Get the current date format yy-mm-dd-hh-mm.log
        val dateFormat = SimpleDateFormat("yyyy-MM-dd-hh-mm")
        val date = dateFormat.format(Date())

        val file = File(dataFolder, "$date.log")
        if(!File(dataFolder).exists()) file.parentFile.mkdirs()
        file.writeText(log.joinToString("\n"))
        if(!file.exists())
            file.createNewFile()
        println("[JesterPets] [INFO]: Saved logs to ${file.path}")

        // Save the traces
        if(traces.isNotEmpty()) {
            val file = File(dataFolder, "$date.traces.log")
            if(!File(dataFolder).exists()) file.parentFile.mkdirs()
            file.writeText(traces.joinToString("\n"))
            if(!file.exists())
                file.createNewFile()
            println("[JesterPets] [TRACE]: Saved traces to ${file.path}")
        }
    }

    fun setLevel(level: Int) {
        this.level = level
    }

    fun setLevel(level: Level) {
        this.level = level.ordinal
    }

    fun clear() {
        log.clear()
        traces.clear()
    }

    fun clearTraces() {
        traces.clear()
    }

    fun clearLogs() {
        log.clear()
    }
}