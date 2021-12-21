package co.com.bancolombia.utils

import co.com.bancolombia.extensions.joinOptions
import co.com.bancolombia.extensions.runCommand
import com.intellij.openapi.project.Project
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Paths


class CommandExecutor(
    private val project: Project
) {

    fun generateStructure(options: Map<String, String>): String {
        val out = "wrapper --gradle-version 6.9.1 --distribution-type all".runCommand(this.project)
        writeBuildGradleFile(options["language"] ?: "JAVA")
        val command =
            "ca ${options.joinOptions()}"

        return "$out \n ${command.runCommand(this.project)}"
    }

    fun generateModel(name: String): String {
        val command = "gm --name=$name"
        return command.runCommand(this.project)
    }

    fun generateUseCase(name: String): String {
        val command = "guc --name=$name"
        return command.runCommand(this.project)
    }

    fun generateEntryPoint(type: EntryPoints, options: Map<String, String>): String {
        val command = "gep --type=${type.name.toLowerCase()} ${options.joinOptions()}"
        return command.runCommand(this.project)
    }

    fun generateDriverAdapter(type: DriverAdapters, options: Map<String, String>): String {
        val command = "gda --type=${type.name.toLowerCase()} ${options.joinOptions()}"
        return command.runCommand(this.project)
    }

    fun generatePipeline(type: String): String {
        val command = "gpl --type=$type"
        return command.runCommand(this.project)
    }

    fun generateHelper(name: String): String {
        val command = "gh --name=$name"
        return command.runCommand(this.project)
    }

    fun deleteModule(name: String): String {
        val command = "dm --module=$name"
        return command.runCommand(this.project)
    }

    private fun writeBuildGradleFile(language: String) {
        var filename = "build.gradle"

        var code = """plugins {
    id "co.com.bancolombia.cleanArchitecture" version "2.0.0"
}
""".trimIndent()

        if (language == "KOTLIN") {
            val pathOfBuildGradle = Paths.get("${project.basePath.toString()}/$filename")
            val pathOfSettingsGradle = Paths.get("${project.basePath.toString()}/settings.gradle")
            try {
                val wasDeletedBuildGradle = Files.deleteIfExists(pathOfBuildGradle)
                val wasDeleteSettingsGradle = Files.deleteIfExists(pathOfSettingsGradle)
                if (wasDeletedBuildGradle && wasDeleteSettingsGradle)
                    println("File is deleted")
                else
                    println("File does not exists")
            } catch (e: IOException) {
                println(e.message)
            }
            filename += ".kts"
            code = """plugins {
    id("co.com.bancolombia.cleanArchitecture") version "2.0.0"
}""".trimIndent()
        }

        val fileWriter = FileWriter("${project.basePath.toString()}/$filename")
        val printWriter = PrintWriter(fileWriter)
        printWriter.print(code)
        printWriter.close()
    }

}

