package co.com.bancolombia.utils

import co.com.bancolombia.extensions.soCommand
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class CommandExecutorTest {

    private val projectDir = File("build", "functionalTest")

    @Test
    fun generateStructure() {
        Assert.assertTrue(
            File(projectDir.absolutePath, "applications")
                .exists()
        )
    }

    @Test
    fun generateModel() {
        CommandExecutor(projectDir.absolutePath).generateModel("user")
        val path: Path =
            Paths.get("domain", "model", "src", "main", "java", "co", "com", "bancolombia", "model", "user")
        Assert.assertTrue(
            File(projectDir.absolutePath, path.toString())
                .exists()
        )
    }

    @Test
    fun generateUseCase() {
        CommandExecutor(projectDir.absolutePath).generateUseCase("product")
        val path: Path =
            Paths.get("domain", "usecase", "src", "main", "java", "co", "com", "bancolombia", "usecase", "product")
        Assert.assertTrue(
            File(projectDir.absolutePath, path.toString())
                .exists()
        )
    }

    @Test
    fun generateEntryPoint() {
        CommandExecutor(projectDir.absolutePath).generateEntryPoint(EntryPoints.GENERIC, mapOf("name" to "NAME"))
        val path: Path =
            Paths.get(
                "infrastructure",
                "entry-points",
                "name",
                "src",
                "main",
                "java",
                "co",
                "com",
                "bancolombia",
                "name"
            )
        Assert.assertTrue(
            File(projectDir.absolutePath, path.toString())
                .exists()
        )

    }

    @Test
    fun generateDriverAdapter() {
        CommandExecutor(projectDir.absolutePath).generateDrivenAdapter(DrivenAdapters.GENERIC, mapOf("name" to "NAME"))
        val path: Path =
            Paths.get(
                "infrastructure",
                "driven-adapters",
                "name",
                "src",
                "main",
                "java",
                "co",
                "com",
                "bancolombia",
                "name"
            )
        Assert.assertTrue(
            File(projectDir.absolutePath, path.toString())
                .exists()
        )
    }

    @Test
    fun generatePipeline() {
        CommandExecutor(projectDir.absolutePath).generatePipeline("azure")
        val path: Path =
            Paths.get("deployment", "${PROJECT_NAME.toString()}_azure_build.yaml")
        Assert.assertTrue(
            File(projectDir.absolutePath, path.toString())
                .exists()
        )
    }

    @Test
    fun generateHelper() {
        CommandExecutor(projectDir.absolutePath).generateHelper("helperName")
        val path: Path =
            Paths.get(
                "infrastructure",
                "helpers",
                "helper-name",
                "src",
                "main",
                "java",
                "co",
                "com",
                "bancolombia",
                "helpername"
            )
        Assert.assertTrue(
            File(projectDir.absolutePath, path.toString())
                .exists()
        )
    }

    @Test
    fun deleteModule() {
        CommandExecutor(projectDir.absolutePath).generateHelper("helperName")
        CommandExecutor(projectDir.absolutePath).deleteModule("helper-name")
        val path: Path =
            Paths.get(
                "infrastructure",
                "helpers",
                "helper-name",
                "src",
                "main",
                "java",
                "co",
                "com",
                "bancolombia",
                "helpername"
            )
        Assert.assertFalse(
            File(projectDir.absolutePath, path.toString())
                .exists()
        )
    }

    @Test
    fun validateProject() {
        val validate = CommandExecutor(projectDir.absolutePath).validateProject()
        Assert.assertTrue(validate)
    }

    @Before
    fun initProject() {
        deleteDirectory(projectDir)
        Files.createDirectories(projectDir.toPath())
        val process = Runtime.getRuntime().exec("init --type basic -p build/functionalTest".soCommand())
        process.waitFor()
        CommandExecutor(projectDir.absolutePath).generateStructure(
            mapOf(
                "type" to "imperative",
                "name" to PROJECT_NAME,
                "coverage" to "jacoco",
                "lombok" to "true"
            )
        )
    }

    private fun deleteDirectory(directoryToBeDeleted: File): Boolean {
        val allContents = directoryToBeDeleted.listFiles()
        if (allContents != null) {
            for (file in allContents) {
                deleteDirectory(file)
            }
        }
        return directoryToBeDeleted.delete()
    }

    @After
    fun tearDown(){
        deleteDirectory(projectDir)
    }

    companion object {
        const val PROJECT_NAME = "TestCA"
    }
}