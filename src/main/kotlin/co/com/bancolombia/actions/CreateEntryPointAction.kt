package co.com.bancolombia.actions

import co.com.bancolombia.utils.CommandExecutor
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CreateEntryPointAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        if (e.project == null || !CommandExecutor(e.project!!.basePath!!).validateProject()) return
        CreateEntryPointDialog(e.project!!).show()
    }
}
