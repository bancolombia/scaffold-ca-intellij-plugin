package co.com.bancolombia.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CreateEntryPointAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        if (e.project == null) return
        CreateEntryPointDialog(e.project!!).show()
    }
}