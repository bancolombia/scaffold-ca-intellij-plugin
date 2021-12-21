package co.com.bancolombia.actions

import co.com.bancolombia.extensions.addLine
import co.com.bancolombia.extensions.initGridBag
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBTextArea
import java.awt.Dimension
import java.awt.GridBagLayout
import javax.swing.JComponent
import javax.swing.JPanel

class OutputDialog(console: String) : DialogWrapper(true) {

    private val panel = JPanel(GridBagLayout())
    private val output = JBTextArea(console)

    init {
        init()
        title = "Output Console"
        panel.preferredSize = Dimension(300, 200)
    }

    override fun createCenterPanel(): JComponent {
        return panel.addLine("out", output, initGridBag())
    }
}
