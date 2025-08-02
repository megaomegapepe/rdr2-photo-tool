package com.megaomegapepe.rdr2.component.ui

import java.awt.Color
import java.awt.GradientPaint
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JPanel

class GradientPanel : JPanel() {
    override fun paintComponent(g: Graphics) {
        val g2 = g.create() as Graphics2D
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)

        val gradient = GradientPaint(
            0f, 0f, Color(20, 30, 48),
            0f, height.toFloat(), Color(36, 59, 85)
        )

        g2.paint = gradient
        g2.fillRect(0, 0, width, height)
        g2.dispose()
    }
}