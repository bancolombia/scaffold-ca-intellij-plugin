package co.com.bancolombia.utils

import co.com.bancolombia.extensions.joinOptions
import co.com.bancolombia.extensions.runCommand
import com.intellij.openapi.project.Project
import java.io.FileWriter
import java.io.PrintWriter


class CommandExecutor(
    private val project: Project
) {

    fun generateStructure(options: Map<String, String>): Unit {

        writeBuildGradleFile(options["language"]?:"JAVA")

        val command =
            "ca ${options.joinOptions()}"
        command.runCommand(this.project)
    }

    fun generateModel(name: String): Unit {
        val command =
            "gm  --name=$name"
        command.runCommand(this.project)
    }

    fun generateUseCase(name: String): Unit {
        val command =
            "guc --name=$name"
        command.runCommand(this.project)
    }

    fun generateEntryPoint(type :EntryPoints, options :Map<String, String>): Unit {
        val command =
            "gep --type=${type.name.toLowerCase()} ${options.joinOptions()}"
       command.runCommand(this.project)
    }

    fun generateDriverAdapter(type :DriverAdapters, options :Map<String, String>): Unit {
        val command =
            "gda --type=${type.name.toLowerCase()} ${options.joinOptions()}"
        command.runCommand(this.project)
    }

    fun generatePipeline(type: String): Unit {
        val command =
            "gpl --type=$type"
        command.runCommand(this.project)
    }

    fun generateHelper(name: String): Unit {
        val command =
            "gh --name=$name"
        command.runCommand(this.project)
    }

    fun deleteModule(name: String): Unit {
        val command =
            "dm --module=$name"
        command.runCommand(this.project)
    }

    private fun writeBuildGradleFile(language: String) {
        var filename = "build.gradle"

        var code = """plugins {
    id "co.com.bancolombia.cleanArchitecture" version "2.0.0"
}""".trimIndent()

        if (language == "KOTLIN") {
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

