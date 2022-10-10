package co.com.bancolombia.actions

import co.com.bancolombia.extensions.addLine
import co.com.bancolombia.extensions.disabledComponent
import co.com.bancolombia.extensions.initGridBag
import co.com.bancolombia.utils.CommandExecutor
import co.com.bancolombia.utils.Language
import co.com.bancolombia.utils.ProjectCoverage
import co.com.bancolombia.utils.ProjectType
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.util.castSafelyTo
import java.awt.Dimension
import java.awt.GridBagLayout
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextArea
import javax.swing.JTextField


class CreateStructureFilesDialog(
    private val project: Project
) : DialogWrapper(true) {

    private val panel = JPanel(GridBagLayout())
    private val filesName = JTextArea("Generation of base files for the implementation of scaffold clean architecture.")

    init {
        init()
        title = "Generate Gradle/Files"
        panel.preferredSize = Dimension(100, 75)
        filesName.isEditable = false
        filesName.autoscrolls = true
    }

    override fun doOKAction() {

        try {
            super.doOKAction()
        } finally {
            Messages.showInfoMessage(
                CommandExecutor(this.project.basePath.toString()).generateStructureFiles(
                    mapOf()
                ),
                "Successful base file generation"
            )
        }
    }

    override fun createCenterPanel(): JComponent {

        val gridBag = initGridBag()
        return panel.addLine("", filesName, gridBag)
    }
}
