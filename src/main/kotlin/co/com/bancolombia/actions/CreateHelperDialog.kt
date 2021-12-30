package co.com.bancolombia.actions

import co.com.bancolombia.extensions.addLine
import co.com.bancolombia.extensions.initGridBag
import co.com.bancolombia.utils.CommandExecutor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.ValidationInfo
import java.awt.Dimension
import java.awt.GridBagLayout
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

class CreateHelperDialog(private val project: Project) : DialogWrapper(true) {

    private val panel = JPanel(GridBagLayout())
    private val helperName = JTextField("helperName")

    init {
        init()
        title = "Generate Helper"
        panel.preferredSize = Dimension(300, 100)
    }

    override fun createCenterPanel(): JComponent {
        return panel.addLine("Name", helperName, initGridBag())
    }

    override fun doValidate(): ValidationInfo? {
        val selected = this.helperName.text
        if (selected.isNullOrEmpty()) {
            return ValidationInfo("Please enter a name", helperName)
        }
        return null
    }

    override fun doOKAction() {
        try {
            super.doOKAction()
        } finally {
            Messages.showInfoMessage(
                CommandExecutor(this.project.basePath.toString()).generateHelper(this.helperName.text),
                "Console Output"
            )
        }
    }
}
