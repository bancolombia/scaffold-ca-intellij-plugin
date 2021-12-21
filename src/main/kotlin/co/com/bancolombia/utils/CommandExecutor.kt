package co.com.bancolombia.utils

import co.com.bancolombia.extensions.joinOptions
import co.com.bancolombia.extensions.runCommand
import com.intellij.openapi.project.Project
import java.io.FileWriter
import java.io.PrintWriter


class CommandExecutor(
    private val project: Project
) {

    fun generateStructure(options: Map<String, String>): String {
        val out = "wrapper --gradle-version $GRADLE_VERSION --distribution-type all".runCommand(this.project)
        writeBuildGradleFile(options["language"] ?: "JAVA")
        val command = "ca ${options.joinOptions()}"
        return "$out \n ${command.runCommand(this.project)}"
    }

    fun generateModel(name: String): String = "gm --name=$name".runCommand(this.project)

    fun generateUseCase(name: String): String = "guc --name=$name".runCommand(this.project)

    fun generateEntryPoint(type: EntryPoints, options: Map<String, String>): String =
        "gep --type=${type.name.toLowerCase()} ${options.joinOptions()}".runCommand(this.project)

    fun generateDriverAdapter(type: DriverAdapters, options: Map<String, String>): String  =
        "gda --type=${type.name.toLowerCase()} ${options.joinOptions()}".runCommand(this.project)

    fun generatePipeline(type: String): String = "gpl --type=$type".runCommand(this.project)

    fun generateHelper(name: String): String = "gh --name=$name".runCommand(this.project)

    fun deleteModule(name: String): String = "dm --module=$name".runCommand(this.project)

    private fun writeBuildGradleFile(language: String) {
        var filename = BUILD_GRADLE
        var content = JAVA_CONTENT
        if (language == Language.KOTLIN.name) {
            deleteGroovyGradleScripts(project)
            filename += ".kts"
            content = KOTLIN_CONTENT
        }

        val fileWriter = FileWriter("${project.basePath.toString()}/$filename")
        val printWriter = PrintWriter(fileWriter)
        printWriter.print(content)
        printWriter.close()
    }

    companion object {
        const val GRADLE_VERSION = "6.9.1"
        const val BUILD_GRADLE = "build.gradle"
        const val SETTINGS_GRADLE = "settings.gradle"
        const val KOTLIN_CONTENT = "plugins {\n\tid(\"co.com.bancolombia.cleanArchitecture\") version \"2.0.0\"\n}"
        const val JAVA_CONTENT = "plugins {\n\tid 'co.com.bancolombia.cleanArchitecture' version '2.0.0'\n}"
    }

}

