package com.megaomegapepe.rdr2.component.ui

import com.megaomegapepe.rdr2.component.Localization
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Component
import java.awt.Font
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.Icon
import javax.swing.JCheckBox

class ModernCheckBox(text: String) : JCheckBox(text) {
    init {
        isOpaque = false
        font = Localization.getFont(Font.PLAIN, 12)
        foreground = Color.WHITE
        icon = createUncheckedIcon()
        selectedIcon = createCheckedIcon()
    }

    private fun createUncheckedIcon(): Icon {
        return object : Icon {
            override fun paintIcon(c: Component?, g: Graphics?, x: Int, y: Int) {
                val g2 = g?.create() as Graphics2D
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
                g2.color = Color(255, 255, 255, 150)
                g2.drawRoundRect(x, y, 16, 16, 4, 4)
                g2.dispose()
            }

            override fun getIconWidth() = 18
            override fun getIconHeight() = 18
        }
    }

    private fun createCheckedIcon(): Icon {
        return object : Icon {
            override fun paintIcon(c: Component?, g: Graphics?, x: Int, y: Int) {
                val g2 = g?.create() as Graphics2D
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

                g2.color = Color(70, 130, 180)
                g2.fillRoundRect(x, y, 16, 16, 4, 4)

                g2.color = Color.WHITE
                g2.stroke = BasicStroke(2f)
                g2.drawPolyline(intArrayOf(x + 4, x + 7, x + 13), intArrayOf(y + 8, y + 11, y + 5), 3)

                g2.dispose()
            }

            override fun getIconWidth() = 18
            override fun getIconHeight() = 18
        }
    }
}