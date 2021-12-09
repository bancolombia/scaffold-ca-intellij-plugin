package com.github.jakspok.scaffoldcaintellijplugin.services

import com.intellij.openapi.project.Project
import com.github.jakspok.scaffoldcaintellijplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
