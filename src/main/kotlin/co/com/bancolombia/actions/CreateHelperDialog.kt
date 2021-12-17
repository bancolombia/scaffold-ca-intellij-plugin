package co.com.bancolombia.actions

import co.com.bancolombia.extensions.customNextLine
import co.com.bancolombia.extensions.customTab
import co.com.bancolombia.extensions.initGridBag
import co.com.bancolombia.utils.CommandExecutor
import co.com.bancolombia.utils.EntryPoints
import co.com.bancolombia.utils.label
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.util.castSafelyTo
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
        val gridBag = initGridBag()
        panel.add(label("Name"), gridBag.customNextLine())
        panel.add(helperName, gridBag.customTab())
        return panel
    }

    override fun doValidate(): ValidationInfo? {
        val selected = this.helperName.text
        if (selected.isNullOrEmpty()) {
            return ValidationInfo("Please enter a name", helperName)
        }
        return null
    }

    override fun doOKAction() {
        val name = this.helperName.text
        CommandExecutor(this.project).generateHelper(name)
        super.doOKAction()
    }
}