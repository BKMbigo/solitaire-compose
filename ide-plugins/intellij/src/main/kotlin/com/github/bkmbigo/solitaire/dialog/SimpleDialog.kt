package com.github.bkmbigo.solitaire.dialog

import androidx.compose.ui.awt.ComposePanel
import com.github.bkmbigo.solitaire.presentation.ui.core.screens.StartScreen
import com.github.bkmbigo.solitaire.presentation.ui.core.theme.SolitaireTheme
import com.github.bkmbigo.solitaire.utils.Platform
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JPanel

class SimpleDialog(
    project: Project
) : DialogWrapper(project, null, true, IdeModalityType.IDE, false) {

    init {
        super.init()
        title = "Solitaire"

        setSize(700, 500)
    }

    override fun createCenterPanel(): JComponent {
        val window = JPanel(BorderLayout())
        window.size = Dimension(700, 700)
        window.border = JBUI.Borders.empty()

        // create ComposePanel
        val composePanel = ComposePanel().also {
            setSize(700, 500)
        }

        composePanel.setContent {
            SolitaireTheme(
                platform = Platform.IDE
            ) {
                StartScreen()
            }
        }
        window.add(composePanel, BorderLayout.CENTER)

        return window
    }
}
