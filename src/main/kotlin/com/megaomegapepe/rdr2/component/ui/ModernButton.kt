package com.megaomegapepe.rdr2.component.ui

import com.megaomegapepe.rdr2.component.Localization
import java.awt.Color
import java.awt.Cursor
import java.awt.Font
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JButton

class ModernButton(text: String) : JButton(text) {
    private var isHovered = false

    init {
        isContentAreaFilled = false
        isFocusPainted = false
        isBorderPainted = false
        font = Localization.getFont(Font.BOLD, 12)
        foreground = Color.WHITE
        cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)

        addMouseListener(object : MouseAdapter() {
            override fun mouseEntered(e: MouseEvent?) {
                isHovered = true
                repaint()
            }

            override fun mouseExited(e: MouseEvent?) {
                isHovered = false
                repaint()
            }
        })
    }

    override fun paintComponent(g: Graphics) {
        val g2 = g.create() as Graphics2D
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        val color = if (isHovered) {
            Color(70, 130, 180, 220)
        } else {
            Color(70, 130, 180, 180)
        }

        g2.color = color
        g2.fillRoundRect(0, 0, width, height, 15, 15)

        g2.dispose()
        super.paintComponent(g)
    }
}