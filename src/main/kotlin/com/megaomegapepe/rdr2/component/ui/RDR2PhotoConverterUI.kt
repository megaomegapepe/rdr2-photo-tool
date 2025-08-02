package com.megaomegapepe.rdr2.component.ui

import com.megaomegapepe.rdr2.component.Localization
import com.megaomegapepe.rdr2.config.localization.Language
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Component
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.Font
import java.awt.GradientPaint
import java.awt.Image
import java.awt.RenderingHints
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.Date
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JComboBox
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.border.EmptyBorder

class RDR2PhotoConverterUI : JFrame() {

    private lateinit var directoryTextField: ModernTextField
    private lateinit var statusLabel: JLabel
    private lateinit var backupCheckBox: ModernCheckBox
    private lateinit var deleteCheckBox: ModernCheckBox
    private lateinit var convertButton: ModernButton
    private lateinit var defaultPathButton: ModernButton
    private lateinit var browseButton: ModernButton
    private lateinit var languageComboBox: JComboBox<Language>

    private lateinit var titleLabel: JLabel
    private lateinit var subtitleLabel: JLabel
    private lateinit var directoryLabel: JLabel
    private lateinit var optionsLabel: JLabel
    private lateinit var languageLabel: JLabel

    private var userName: String = ""
    private var defaultDirPRDR: String = ""
    private var activeDir: String = ""
    private var backupDirPRDR: String = ""
    private var convertedFilesDir: String = ""

    private val prdrFiles: MutableList<String> = ArrayList()

    init {
        initializeComponent()
        setupDirectories()
    }

    private fun initializeComponent() {
        title = Localization.get("app_title")
        defaultCloseOperation = EXIT_ON_CLOSE
        layout = BorderLayout()

        try {
            val icon = createAppIcon()
            iconImage = icon
        } catch (e: Exception) {
            println("Can't load icon: ${e.message}")
        }

        userName = System.getProperty("user.name")

        val mainPanel = GradientPanel()
        mainPanel.layout = BorderLayout()
        mainPanel.border = EmptyBorder(30, 30, 30, 30)

        val languagePanel = createLanguagePanel()

        val titlePanel = createTitlePanel()

        val directoryPanel = createDirectoryPanel()

        val optionsPanel = createOptionsPanel()

        val buttonsPanel = createButtonsPanel()

        createStatusBar()

        val centerPanel = JPanel()
        centerPanel.isOpaque = false
        centerPanel.layout = BoxLayout(centerPanel, BoxLayout.Y_AXIS)
        centerPanel.add(languagePanel)
        centerPanel.add(Box.createVerticalStrut(20))
        centerPanel.add(titlePanel)
        centerPanel.add(Box.createVerticalStrut(30))
        centerPanel.add(directoryPanel)
        centerPanel.add(Box.createVerticalStrut(20))
        centerPanel.add(optionsPanel)
        centerPanel.add(Box.createVerticalStrut(30))
        centerPanel.add(buttonsPanel)

        mainPanel.add(centerPanel, BorderLayout.CENTER)
        add(mainPanel, BorderLayout.CENTER)
        add(statusLabel, BorderLayout.SOUTH)

        // Window settings
        setSize(700, 550)
        setLocationRelativeTo(null)
        setResizable(false)
    }

    private fun createLanguagePanel(): JPanel {
        val panel = JPanel(FlowLayout(FlowLayout.RIGHT))
        panel.isOpaque = false

        languageLabel = JLabel(Localization.get("language"))
        languageLabel.font = Localization.getFont(Font.PLAIN, 12)
        languageLabel.foreground = Color.WHITE

        languageComboBox = JComboBox(Language.values())
        languageComboBox.selectedItem = Localization.getCurrentLanguage()
        languageComboBox.font = Localization.getFont(Font.PLAIN, 12)
        languageComboBox.preferredSize = Dimension(120, 25)

        languageComboBox.addActionListener {
            val selectedLanguage = languageComboBox.selectedItem as Language
            Localization.setLanguage(selectedLanguage)
            updateLanguage()
        }

        panel.add(languageLabel)
        panel.add(Box.createHorizontalStrut(5))
        panel.add(languageComboBox)

        return panel
    }

    private fun updateLanguage() {
        title = Localization.get("app_title")

        titleLabel.text = Localization.get("window_title")
        titleLabel.font = Localization.getFont(Font.BOLD, 28)

        subtitleLabel.text = Localization.get("subtitle")
        subtitleLabel.font = Localization.getFont(Font.PLAIN, 14)

        directoryLabel.text = Localization.get("select_directory")
        directoryLabel.font = Localization.getFont(Font.BOLD, 14)

        optionsLabel.text = Localization.get("conversion_options")
        optionsLabel.font = Localization.getFont(Font.BOLD, 14)

        languageLabel.text = Localization.get("language")
        languageLabel.font = Localization.getFont(Font.PLAIN, 12)

        defaultPathButton.text = Localization.get("default_path")
        defaultPathButton.font = Localization.getFont(Font.BOLD, 12)

        browseButton.text = Localization.get("browse")
        browseButton.font = Localization.getFont(Font.BOLD, 12)

        convertButton.text = Localization.get("convert_files")
        convertButton.font = Localization.getFont(Font.BOLD, 14)

        backupCheckBox.text = Localization.get("create_backup")
        backupCheckBox.font = Localization.getFont(Font.PLAIN, 12)

        deleteCheckBox.text = Localization.get("delete_original")
        deleteCheckBox.font = Localization.getFont(Font.PLAIN, 12)

        directoryTextField.font = Localization.getFont(Font.PLAIN, 12)

        languageComboBox.font = Localization.getFont(Font.PLAIN, 12)

        statusLabel.text = Localization.get("status_ready")
        statusLabel.font = Localization.getFont(Font.PLAIN, 12)

        // Render interface
        repaint()
        revalidate()
    }

    private fun createAppIcon(): Image {
        val size = 64
        val image = java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB)
        val g2 = image.createGraphics()

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        val gradient = GradientPaint(0f, 0f, Color(70, 130, 180), size.toFloat(), size.toFloat(), Color(100, 149, 237))
        g2.paint = gradient
        g2.fillRoundRect(8, 8, size - 16, size - 16, 12, 12)

        g2.color = Color.WHITE
        g2.fillRoundRect(18, 22, 28, 20, 4, 4)
        g2.fillRoundRect(26, 18, 12, 6, 2, 2)
        g2.color = Color(70, 130, 180)
        g2.fillOval(28, 28, 8, 8)

        g2.dispose()
        return image
    }

    private fun createTitlePanel(): JPanel {
        val panel = JPanel()
        panel.isOpaque = false
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)

        titleLabel = JLabel(Localization.get("window_title"))
        titleLabel.font = Localization.getFont(Font.BOLD, 28)
        titleLabel.foreground = Color.WHITE
        titleLabel.alignmentX = Component.CENTER_ALIGNMENT

        subtitleLabel = JLabel(Localization.get("subtitle"))
        subtitleLabel.font = Localization.getFont(Font.PLAIN, 14)
        subtitleLabel.foreground = Color(200, 200, 200)
        subtitleLabel.alignmentX = Component.CENTER_ALIGNMENT

        panel.add(titleLabel)
        panel.add(Box.createVerticalStrut(5))
        panel.add(subtitleLabel)

        return panel
    }

    private fun createDirectoryPanel(): JPanel {
        val panel = JPanel()
        panel.isOpaque = false
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)

        directoryLabel = JLabel(Localization.get("select_directory"))
        directoryLabel.font = Localization.getFont(Font.BOLD, 14)
        directoryLabel.foreground = Color.WHITE
        directoryLabel.alignmentX = Component.LEFT_ALIGNMENT

        directoryTextField = ModernTextField()
        directoryTextField.preferredSize = Dimension(500, 35)
        directoryTextField.maximumSize = Dimension(Integer.MAX_VALUE, 35)

        val buttonPanel = JPanel(FlowLayout(FlowLayout.LEFT, 0, 10))
        buttonPanel.isOpaque = false

        defaultPathButton = ModernButton(Localization.get("default_path"))
        browseButton = ModernButton(Localization.get("browse"))

        defaultPathButton.preferredSize = Dimension(150, 35)
        browseButton.preferredSize = Dimension(100, 35)

        defaultPathButton.addActionListener { onDefaultPathClicked() }
        browseButton.addActionListener { onBrowseClicked() }

        buttonPanel.add(defaultPathButton)
        buttonPanel.add(Box.createHorizontalStrut(10))
        buttonPanel.add(browseButton)

        panel.add(directoryLabel)
        panel.add(Box.createVerticalStrut(10))
        panel.add(directoryTextField)
        panel.add(buttonPanel)

        return panel
    }

    private fun createOptionsPanel(): JPanel {
        val panel = JPanel()
        panel.isOpaque = false
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)

        optionsLabel = JLabel(Localization.get("conversion_options"))
        optionsLabel.font = Localization.getFont(Font.BOLD, 14)
        optionsLabel.foreground = Color.WHITE
        optionsLabel.alignmentX = Component.LEFT_ALIGNMENT

        backupCheckBox = ModernCheckBox(Localization.get("create_backup"))
        deleteCheckBox = ModernCheckBox(Localization.get("delete_original"))

        backupCheckBox.alignmentX = Component.LEFT_ALIGNMENT
        deleteCheckBox.alignmentX = Component.LEFT_ALIGNMENT

        panel.add(optionsLabel)
        panel.add(Box.createVerticalStrut(15))
        panel.add(backupCheckBox)
        panel.add(Box.createVerticalStrut(10))
        panel.add(deleteCheckBox)

        return panel
    }

    private fun createButtonsPanel(): JPanel {
        val panel = JPanel()
        panel.isOpaque = false
        panel.layout = BoxLayout(panel, BoxLayout.X_AXIS)

        convertButton = ModernButton(Localization.get("convert_files"))
        convertButton.preferredSize = Dimension(250, 45)
        convertButton.font = Localization.getFont(Font.BOLD, 14)
        convertButton.addActionListener { onConvertFilesClicked() }

        panel.add(Box.createHorizontalGlue())
        panel.add(convertButton)
        panel.add(Box.createHorizontalGlue())

        return panel
    }

    private fun createStatusBar() {
        statusLabel = JLabel(Localization.get("status_ready"))
        statusLabel.font = Localization.getFont(Font.PLAIN, 12)
        statusLabel.foreground = Color(100, 100, 100)
        statusLabel.background = Color(240, 240, 240)
        statusLabel.isOpaque = true
        statusLabel.border = EmptyBorder(8, 15, 8, 15)
    }

    private fun setupDirectories() {
        try {
            setDefaultDirectory()
            setAppDirectories()
            directoryTextField.text = defaultDirPRDR
            activeDir = defaultDirPRDR
            statusLabel.text = Localization.get("status_default_path")
        } catch (e: Exception) {
            showError("${Localization.get("directory_error")} ${e.message}")
        }
    }

    private fun setDefaultDirectory() {
        try {
            val documentsPath = System.getProperty("user.home") + "/Documents"
            val rdr2ProfilesPath = "$documentsPath/Rockstar Games/Red Dead Redemption 2/Profiles"

            val profilesDir = File(rdr2ProfilesPath)
            if (profilesDir.exists() && profilesDir.isDirectory) {
                val profiles = profilesDir.listFiles { file -> file.isDirectory }
                if (profiles != null && profiles.isNotEmpty()) {
                    defaultDirPRDR = profiles[0].absolutePath
                    return
                }
            }

            defaultDirPRDR = System.getProperty("user.home")

        } catch (e: Exception) {
            defaultDirPRDR = System.getProperty("user.home")
            showError(Localization.get("rdr2_not_found"))
        }
    }

    private fun setAppDirectories() {
        val picturesPath = System.getProperty("user.home") + "/Pictures"
        convertedFilesDir = "$picturesPath/RDR2 Photos"
        backupDirPRDR = "$convertedFilesDir/prdr backups"

        File(convertedFilesDir).mkdirs()
        File(backupDirPRDR).mkdirs()
    }

    private fun onDefaultPathClicked() {
        directoryTextField.text = defaultDirPRDR
        statusLabel.text = Localization.get("status_default_path")
    }

    private fun onBrowseClicked() {
        val fileChooser = JFileChooser()
        fileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        fileChooser.currentDirectory = File(directoryTextField.text.ifEmpty { System.getProperty("user.home") })

        val result = fileChooser.showOpenDialog(this)
        if (result == JFileChooser.APPROVE_OPTION) {
            directoryTextField.text = fileChooser.selectedFile.absolutePath
            statusLabel.text = Localization.get("status_custom_path")
        } else {
            statusLabel.text = Localization.get("status_no_folder")
        }
    }

    private fun onConvertFilesClicked() {
        if (!getCustomDir()) return

        // Show progress
        convertButton.text = Localization.get("converting")
        convertButton.isEnabled = false

        SwingUtilities.invokeLater {
            try {
                getValidFiles(activeDir)

                if (prdrFiles.isEmpty()) {
                    showMessage(Localization.get("no_files_found"))
                    resetConvertButton()
                    return@invokeLater
                }

                var backupInfo = ""
                if (backupCheckBox.isSelected) {
                    backupInfo = backupPRDRs()
                }

                var convertedCount = 0
                var errorCount = 0

                for (file in prdrFiles) {
                    try {
                        val metadata = getMetaData(file)
                        val originalFileName = File(file).name
                        val fileName = "$metadata $originalFileName"

                        val fileBytes = Files.readAllBytes(Paths.get(file))

                        if (fileBytes.size > 300) {
                            val imageBytes = fileBytes.copyOfRange(300, fileBytes.size)

                            val outputFile = File(convertedFilesDir, "$fileName.jpg")
                            Files.write(outputFile.toPath(), imageBytes)
                            convertedCount++

                            if (deleteCheckBox.isSelected) {
                                File(file).delete()
                            }
                        } else {
                            errorCount++
                            println("File $file is too tiny")
                        }

                    } catch (e: Exception) {
                        errorCount++
                        println("Error while convert $file: ${e.message}")
                    }
                }

                val statusMessage = if (backupInfo.isNotEmpty()) {
                    "${
                        Localization.get("status_ready").replace("Готов к работе", "").replace("Ready to work", "")
                    } $backupInfo $convertedCount ${Localization.get("files_converted_to_images")}"
                } else {
                    "${
                        Localization.get("status_ready").replace("Готов к работе", "").replace("Ready to work", "")
                    } $convertedCount ${Localization.get("files_converted_to_images")}"
                }

                if (errorCount > 0) {
                    statusLabel.text = "$statusMessage ${Localization.get("errors")} $errorCount"
                } else {
                    statusLabel.text = statusMessage
                }

                prdrFiles.clear()

                showMessage("${Localization.get("conversion_complete")}\n\n$convertedCount ${Localization.get("files_converted")}")

            } finally {
                resetConvertButton()
            }
        }
    }

    private fun resetConvertButton() {
        convertButton.text = Localization.get("convert_files")
        convertButton.isEnabled = true
    }

    private fun getCustomDir(): Boolean {
        val customDirPRDR = directoryTextField.text.trim()
        activeDir = customDirPRDR

        return if (File(customDirPRDR).exists()) {
            statusLabel.text = Localization.get("status_valid_path")
            true
        } else {
            statusLabel.text = Localization.get("status_invalid_path")
            showError(Localization.get("invalid_path_message").replace("\\n", "\n"))
            false
        }
    }

    private fun getValidFiles(path: String) {
        prdrFiles.clear()

        try {
            val directory = File(path)
            val files = directory.listFiles() ?: return

            for (file in files) {
                if (file.isFile && file.name.contains("PRDR", ignoreCase = true)) {
                    prdrFiles.add(file.absolutePath)
                }
            }

            statusLabel.text = "${
                Localization.get("status_ready").replace("Готов к работе", "").replace("Ready to work", "")
            } ${Localization.get("found_files")} ${prdrFiles.size} ${Localization.get("prdr_files_ready")}"

        } catch (e: Exception) {
            showError("${Localization.get("files_search_error")} ${e.message}")
        }
    }

    private fun getMetaData(filePath: String): String {
        return try {
            val fileBytes = Files.readAllBytes(Paths.get(filePath))
            var dataString = ""

            for (i in 20 until minOf(54, fileBytes.size)) {
                if (fileBytes[i] > 31) {
                    dataString += fileBytes[i].toInt().toChar()
                }
            }

            val parts = dataString.trim().split(" ")
            if (parts.size >= 2) {
                val dateParts = parts[0].split("/")
                val timeParts = parts[1].split(":")

                if (dateParts.size == 3 && timeParts.size >= 3) {
                    val month = dateParts[0].padStart(2, '0')
                    val day = dateParts[1].padStart(2, '0')
                    val year = dateParts[2]
                    val hour = timeParts[0].padStart(2, '0')
                    val minute = timeParts[1].padStart(2, '0')
                    val second = timeParts[2].padStart(2, '0')

                    return "$year-$month-$day $hour.$minute.$second"
                }
            }

            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH.mm.ss")
            dateFormat.format(Date())

        } catch (e: Exception) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH.mm.ss")
            dateFormat.format(Date())
        }
    }

    private fun backupPRDRs(): String {
        var backedUpFiles = 0
        var duplicateFiles = 0

        for (file in prdrFiles) {
            try {
                val sourceFile = File(file)
                val metadata = getMetaData(file)
                val backupFileName = "$metadata ${sourceFile.name}"
                val backupFile = File(backupDirPRDR, backupFileName)

                if (!backupFile.exists()) {
                    Files.copy(sourceFile.toPath(), backupFile.toPath())
                    backedUpFiles++
                } else {
                    duplicateFiles++
                }

            } catch (e: Exception) {
                println("Error while create backup $file: ${e.message}")
            }
        }

        return "$backedUpFiles ${Localization.get("files_backed_up")} $duplicateFiles ${Localization.get("duplicates")}"
    }

    private fun showMessage(message: String) {
        val optionPane = JOptionPane(message, JOptionPane.INFORMATION_MESSAGE)
        val dialog = optionPane.createDialog(this, Localization.get("information"))
        dialog.isVisible = true
    }

    private fun showError(message: String) {
        val optionPane = JOptionPane(message, JOptionPane.ERROR_MESSAGE)
        val dialog = optionPane.createDialog(this, Localization.get("error"))
        dialog.isVisible = true
    }
}