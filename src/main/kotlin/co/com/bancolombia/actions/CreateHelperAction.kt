package co.com.bancolombia.actions

import co.com.bancolombia.utils.CommandExecutor
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import java.util.*
import java.util.stream.Collectors


class CreateHelperAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        if (e.project == null || !CommandExecutor(e.project!!.basePath!!).validateProject()) return
        CreateHelperDialog(e.project!!).show()
    }
}
