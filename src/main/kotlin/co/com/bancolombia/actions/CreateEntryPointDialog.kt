package co.com.bancolombia.actions

import co.com.bancolombia.extensions.customNextLine
import co.com.bancolombia.extensions.customTab
import co.com.bancolombia.extensions.initGridBag
import co.com.bancolombia.utils.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.util.castSafelyTo
import java.awt.Dimension
import java.awt.GridBagLayout
import java.awt.event.ActionListener
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField


class CreateEntryPointDialog(
    private val project: Project
) : DialogWrapper(true) {

    private val panel = JPanel(GridBagLayout())
    private val type = ComboBox(EntryPoints.values())
    private val name = JTextField()
    private val server = ComboBox(ServerOptions.values())
    private val router = JCheckBox("router", true)
    private val graphQL = JTextField("graphql")
    private val options = mutableMapOf<String, String>()

    init {
        init()
        title = "Generate EntryPoint"
        panel.preferredSize = Dimension(300, 250)

    }

    override fun doOKAction() {
        val type = this.type.selectedItem.castSafelyTo<EntryPoints>() ?: EntryPoints.NONE
        if (graphQL.isEnabled) {
            options["pathgql"] = graphQL.text
        }

        CommandExecutor(this.project).generateEntryPoint(type, options)
        super.doOKAction()
    }

    override fun createCenterPanel(): JComponent {

        name.isEnabled = false
        server.isEnabled = false
        router.isEnabled = false
        graphQL.isEnabled = false

        type.addActionListener(ActionListener {
            val selectedType = type.selectedItem.castSafelyTo<EntryPoints>() ?: EntryPoints.NONE
            name.isEnabled = false
            server.isEnabled = false
            router.isEnabled = false
            graphQL.isEnabled = false
            options.clear()
            enableFields(selectedType)
        })
        server.addActionListener(ActionListener {
            val selectedServer = server.selectedItem.castSafelyTo<ServerOptions>() ?: ServerOptions.UNDERTOW
            options["server"] = selectedServer.name.toLowerCase()
        })
        router.addActionListener(ActionListener {
            val selectedType = router.isSelected.toString()
            options["router"] = selectedType
        })

        val gridBag = initGridBag()
        panel.add(label("Type"), gridBag.customNextLine())
        panel.add(type, gridBag.customTab())
        panel.add(label("Name"), gridBag.customNextLine())
        panel.add(name, gridBag.customTab())
        panel.add(label("Server"), gridBag.customNextLine())
        panel.add(server, gridBag.customTab())
        panel.add(router, gridBag.customNextLine())
        panel.add(label("GraphQl"), gridBag.customNextLine())
        panel.add(graphQL, gridBag.customTab())

        return panel
    }

    private fun enableFields(type: EntryPoints) = when (type) {
        EntryPoints.GENERIC -> name.isEnabled = true
        EntryPoints.RESTMVC -> server.isEnabled = true
        EntryPoints.WEBFLUX -> router.isEnabled = true
        EntryPoints.GRAPHQL -> graphQL.isEnabled = true
        else -> {}
    }

}