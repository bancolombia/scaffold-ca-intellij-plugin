package co.com.bancolombia.extensions

import co.com.bancolombia.utils.label
import com.intellij.mock.Mock
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.util.ui.GridBag
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import javax.swing.JPanel
import javax.swing.JTextField
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class TestExtensions {

    @Test
    fun `test join option in map collection should  returns a string`() {
        assertEquals("--name=NAME", mapOf("name" to "NAME").joinOptions())

    }

    @Test
    fun `test soCommand should  returns a stringCommand linux`() {
        System.setProperty("os.name", "linux")
        assertEquals("./gradlew command", "command".soCommand())
    }

    @Test
    fun `test runCommand should  returns a string`() {
        assertTrue("version".runCommand(".").isNotEmpty())
    }

    @Test
    fun `test instance GridBag should  returns a GridBag`() {
        val gridBag = initGridBag()
        assertNotNull(gridBag)
        assertNotNull(gridBag.customNextLine())
        assertNotNull(gridBag.customTab())
    }

    @Test
    fun `test enable component should visible component`() {
        val deleteModuleName = JTextField("moduleName")
        deleteModuleName.enabledComponent()
        assertTrue(deleteModuleName.isEnabled)
        assertTrue(deleteModuleName.isVisible)
    }

    @Test
    fun `test disable component should not visible component`() {
        val deleteModuleName = JTextField("moduleName")
        deleteModuleName.disabledComponent()
        assertFalse(deleteModuleName.isEnabled)
        assertFalse(deleteModuleName.isVisible)
    }

    @Test
    fun `test add component to grid`() {
        val deleteModuleName = JTextField("moduleName")
        val jPanel = JPanel()
        jPanel.addLine("Tets" ,deleteModuleName, initGridBag())
        assertTrue(jPanel.components.contains(deleteModuleName))
    }

    @Test
    fun `test add component to grid with label`() {
        val deleteModuleName = JTextField("moduleName")
        val jPanel = JPanel()
        jPanel.addLine(label("Name") ,deleteModuleName, initGridBag())
        assertTrue(jPanel.components.contains(deleteModuleName))
    }




}