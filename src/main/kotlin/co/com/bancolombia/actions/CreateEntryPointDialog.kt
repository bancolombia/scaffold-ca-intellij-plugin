package co.com.bancolombia.actions

import co.com.bancolombia.extensions.addLine
import co.com.bancolombia.extensions.disabledComponent
import co.com.bancolombia.extensions.enabledComponent
import co.com.bancolombia.extensions.initGridBag
import co.com.bancolombia.utils.CommandExecutor
import co.com.bancolombia.utils.EntryPoints
import co.com.bancolombia.utils.ServerOptions
import co.com.bancolombia.utils.label
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
    private val nameLabel = label("Name")
    private val serverLabel = label("Server")
    private val graphQLabel = label("Name")

    init {
        init()
        title = "Generate EntryPoint"
        panel.preferredSize = Dimension(300, 100)
    }

    override fun doOKAction() {
        val type = this.type.selectedItem.castSafelyTo<EntryPoints>() ?: EntryPoints.NONE
        if (graphQL.isEnabled) {
            options["pathgql"] = graphQL.text
        }
        if (name.isEnabled) {
            options["name"] = name.text
        }
        if (router.isEnabled) {
            options["router"] = router.isSelected.toString()
        }

        try {
            super.doOKAction()
        } finally {
            Messages.showInfoMessage(
                CommandExecutor(this.project.basePath.toString()).generateEntryPoint(type, options),
                "Console Output"
            )
        }
    }

    override fun doValidate(): ValidationInfo? {
        val selected = this.type.selectedItem.castSafelyTo<EntryPoints>()
        if (selected == EntryPoints.NONE) {
            return ValidationInfo("Please make a selection", type)
        }
        if (selected == EntryPoints.GENERIC && name.text.isNullOrEmpty()) {
            return ValidationInfo(EMPTY_FIELD_MESSAGE, name)
        }
        if (selected == EntryPoints.WEBFLUX && router.text.isNullOrEmpty()) {
            return ValidationInfo(EMPTY_FIELD_MESSAGE, router)
        }
        if (selected == EntryPoints.GRAPHQL && graphQL.text.isNullOrEmpty()) {
            return ValidationInfo(EMPTY_FIELD_MESSAGE, graphQL)
        }
        return null
    }

    override fun createCenterPanel(): JComponent {
        val grid = initGridBag()
        disabledComponents()
        type.addActionListener {
            val selectedType = type.selectedItem.castSafelyTo<EntryPoints>() ?: EntryPoints.NONE
            disabledComponents()
            options.clear()
            enableFields(selectedType)
        }
        server.addActionListener {
            val selectedServer =
                server.selectedItem.castSafelyTo<ServerOptions>() ?: ServerOptions.UNDERTOW
            options["server"] = selectedServer.name.toLowerCase()
        }
        return panel.addLine(label("Entry Point"), type, grid)
            .addLine(nameLabel, name, grid)
            .addLine(serverLabel, server, grid)
            .addLine("", router, grid)
            .addLine(graphQLabel, graphQL, grid)
    }


    private fun disabledComponents() {
        name.disabledComponent()
        nameLabel.disabledComponent()
        server.disabledComponent()
        serverLabel.disabledComponent()
        router.disabledComponent()
        graphQL.disabledComponent()
        graphQLabel.disabledComponent()
    }

    private fun enableFields(type: EntryPoints) = when (type) {
        EntryPoints.GENERIC -> {
            name.enabledComponent()
            nameLabel.enabledComponent()
        }
        EntryPoints.RESTMVC -> {
            server.enabledComponent()
            serverLabel.enabledComponent()
        }
        EntryPoints.WEBFLUX -> router.enabledComponent()
        EntryPoints.GRAPHQL -> {
            graphQL.enabledComponent()
            graphQLabel.enabledComponent()
        }
        else -> null
    }

    companion object {
        const val EMPTY_FIELD_MESSAGE = "The field can't be empty"
    }
}
