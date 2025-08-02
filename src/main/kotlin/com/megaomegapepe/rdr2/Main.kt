package com.megaomegapepe.rdr2

import com.megaomegapepe.rdr2.component.ui.RDR2PhotoConverterUI
import javax.swing.SwingUtilities
import javax.swing.UIManager

fun main() {
    SwingUtilities.invokeLater {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val converter = RDR2PhotoConverterUI()
        converter.isVisible = true
    }
}