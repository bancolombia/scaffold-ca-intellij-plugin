package co.com.bancolombia.utils

import co.com.bancolombia.extensions.joinOptions
import co.com.bancolombia.extensions.runCommand
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter


class CommandExecutor(
    private val basePath: String
) {

    fun generateStructure(options: Map<String, String>): String {

        writeBuildGradleFile(options["language"] ?: "JAVA")



        var out = ""
        if (" --version".runCommand(this.basePath).contains("Gradle [1-6]".toRegex())) {
            out = "wrapper --gradle-version $GRADLE_VERSION --distribution-type all".runCommand(this.basePath)
        }

        val command = "ca ${options.joinOptions()}"
        return "$out \n ${command.runCommand(this.basePath)}"
    }

    fun generateModel(name: String): String = "gm --name=$name".runCommand(this.basePath)

    fun generateUseCase(name: String): String = "guc --name=$name".runCommand(this.basePath)

    fun generateEntryPoint(type: EntryPoints, options: Map<String, String>): String =
        "gep --type=${type.name.lowercase()} ${options.joinOptions()}".runCommand(this.basePath)

    fun generateDrivenAdapter(type: DrivenAdapters, options: Map<String, String>): String =
        "gda --type=${type.name.lowercase()} ${options.joinOptions()}".runCommand(this.basePath)

    fun generatePipeline(type: String): String = "gpl --type=$type".runCommand(this.basePath)

    fun generateHelper(name: String): String = "gh --name=$name".runCommand(this.basePath)

    fun deleteModule(name: String): String = "dm --module=$name".runCommand(this.basePath)

    fun validateProject(): Boolean {
        return File(basePath, "gradle.properties").exists() && "task".runCommand(this.basePath)
            .contains("cleanArchitecture".toRegex())
    }

    private fun writeBuildGradleFile(language: String) {

        var buildFile = BUILD_GRADLE
        var settingsFile = SETTINGS_GRADLE
        var content = JAVA_CONTENT

        deleteGroovyGradleScripts(basePath)

        if (language == Language.KOTLIN.name) {
            buildFile += ".kts"
            settingsFile += ".kts"
            content = KOTLIN_CONTENT
        }

        val fileWriter = FileWriter("${basePath}/$buildFile")
        FileWriter("${basePath}/$settingsFile")
        val printWriter = PrintWriter(fileWriter)
        printWriter.print(content)
        printWriter.close()
    }

    companion object {
        const val GRADLE_VERSION = "7.5"
        const val BUILD_GRADLE = "build.gradle"
        const val SETTINGS_GRADLE = "settings.gradle"
        const val KOTLIN_CONTENT = "plugins {\n\tid(\"co.com.bancolombia.cleanArchitecture\") version \"2.4.5\"\n}"
        const val JAVA_CONTENT = "plugins {\n\tid 'co.com.bancolombia.cleanArchitecture' version '2.4.5'\n}"
    }
}

