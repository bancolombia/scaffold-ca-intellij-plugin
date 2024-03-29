package co.com.bancolombia.actions

import co.com.bancolombia.utils.CommandExecutor
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CreateStructureFilesAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        if (e.project == null || CommandExecutor(e.project!!.basePath!!).validateProject())
            return
        CreateStructureFilesDialog(e.project!!).show()
    }
}
