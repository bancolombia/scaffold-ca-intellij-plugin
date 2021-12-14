package co.com.bancolombia.actions

import co.com.bancolombia.extensions.runCommand
import co.com.bancolombia.utils.label
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.uiDesigner.core.AbstractLayout
import com.intellij.util.ui.GridBag
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

class CreateUseCaseDialog(private val project: Project) : DialogWrapper(true) {


    private val panel = JPanel(GridBagLayout())
    private val useCaseName = JTextField("useCaseName")

    init {
        init()
        title = "Generate Use Case"
    }

    override fun createCenterPanel(): JComponent? {
        panel.preferredSize = Dimension(300, 100)

        val gridBag = GridBag().setDefaultInsets(
            Insets(
                0,
                0,
                AbstractLayout.DEFAULT_VGAP,
                AbstractLayout.DEFAULT_HGAP
            )
        ).setDefaultWeightX(1.0).setDefaultFill(GridBagConstraints.HORIZONTAL)
        panel.add(label("Name"), gridBag.nextLine().next().weightx(0.2))
        panel.add(useCaseName, gridBag.next().weightx(0.8))
        return panel
    }

    override fun doOKAction() {
        val name = this.useCaseName.text
        val command =
            "guc --name=$name "
        command.runCommand(this.project)
        super.doOKAction()
    }
}