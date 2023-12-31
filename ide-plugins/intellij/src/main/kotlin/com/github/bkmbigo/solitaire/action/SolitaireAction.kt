package com.github.bkmbigo.solitaire.action

import com.github.bkmbigo.solitaire.dialog.SimpleDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class SolitaireAction: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let { SimpleDialog(it).show() }
    }
}
