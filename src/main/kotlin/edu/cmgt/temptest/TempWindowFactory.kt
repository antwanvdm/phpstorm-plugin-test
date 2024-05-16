package edu.cmgt.temptest

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.Gray
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.panels.VerticalLayout
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.Color
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.*
import javax.swing.border.CompoundBorder
import javax.swing.border.MatteBorder

class TempWindowFactory : ToolWindowFactory {
    private val chatMessagesPanel = JPanel(VerticalLayout(1))
    private val chatMessagesScrollPanel = JBScrollPane(chatMessagesPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)

    /**
     * ISSUE: Title doesn't show
     */
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        toolWindow.title = "Hallo Iedereen"
        val mainPanel = JPanel()
        mainPanel.layout = BorderLayout()

        val topText = JTextPane()
        topText.text = "My top layer"
        mainPanel.add(topText, BorderLayout.NORTH)

        chatMessagesPanel.add(chatMessagePanel("Hi! This is mij first message"))
        chatMessagesPanel.add(chatMessagePanel("Another message"))
        chatMessagesPanel.add(chatMessagePanel("Another one"))

        mainPanel.add(chatMessagesScrollPanel, BorderLayout.CENTER)
        mainPanel.add(inputPanel(), BorderLayout.SOUTH)

        toolWindow.component.add(mainPanel)
    }

    /**
     * ISSUE: background color doesn't work on initial render, only after form submit
     */
    private fun chatMessagePanel(text: String): JPanel {
        val chatPanel = JPanel(VerticalLayout(1))
        chatPanel.isOpaque = true
        chatPanel.background = JBColor(Color(235, 236, 240), Color(57, 59, 64))
        chatPanel.border = CompoundBorder(MatteBorder(1, 0, 0, 0, Gray._110), JBUI.Borders.empty(10, 0))

        val textPane = JTextPane()
        textPane.text = text
        textPane.border = BorderFactory.createEmptyBorder(0, 15, 0, 20)
        textPane.isOpaque = false
        chatPanel.add(textPane)

        return chatPanel
    }

    private fun inputPanel(): JTextField {
        val inputField = JTextField(20)
        inputField.addKeyListener(object : KeyListener {
            override fun keyTyped(e: KeyEvent?) {}

            override fun keyPressed(e: KeyEvent?) {
                if (e?.keyCode == KeyEvent.VK_ENTER) {
                    chatMessagesPanel.add(chatMessagePanel("after form send works, nice background!"))
                }
            }

            override fun keyReleased(e: KeyEvent?) {}
        })
        return inputField
    }
}