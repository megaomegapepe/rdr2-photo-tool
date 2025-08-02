package com.megaomegapepe.rdr2.component.ui

import com.megaomegapepe.rdr2.component.Localization
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JTextField
import javax.swing.border.EmptyBorder

class ModernTextField : JTextField() {
    init {
        background = Color(255, 255, 255, 220)
        border = EmptyBorder(8, 12, 8, 12)
        font = Localization.getFont(Font.PLAIN, 12)
        foreground = Color(50, 50, 50)
    }

    override fun paintComponent(g: Graphics) {
        val g2 = g.create() as Graphics2D
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        g2.color = background
        g2.fillRoundRect(0, 0, width, height, 8, 8)

        g2.dispose()
        super.paintComponent(g)
    }
}