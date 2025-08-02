package com.megaomegapepe.rdr2.component

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.megaomegapepe.rdr2.config.localization.Language
import com.megaomegapepe.rdr2.config.localization.LanguageConfig
import com.megaomegapepe.rdr2.config.localization.LocalizationData
import java.awt.Font
import java.awt.GraphicsEnvironment
import java.io.File

object Localization {
    private var currentLanguage = Language.ENGLISH
    private val objectMapper = ObjectMapper().registerModule(KotlinModule.Builder().build())
    private var localizationData: LocalizationData? = null

    init {
        loadLocalizationData()
    }

    private fun loadLocalizationData() {
        try {
            val inputStream = javaClass.classLoader.getResourceAsStream("localization.json")
            if (inputStream != null) {
                localizationData = objectMapper.readValue(inputStream)
                println("Localization loaded from resources/localization.json")
            } else {
                // If there is no localization.json file - then create
                createDefaultLocalizationFile()
                loadDefaultLocalization()
                println("Created localization file by default config")
            }
        } catch (e: Exception) {
            println("Error while loading localization file: ${e.message}")
            loadDefaultLocalization()
        }
    }

    private fun createDefaultLocalizationFile() {
        val defaultData = getDefaultLocalizationData()
        try {
            val resourcesDir = File("src/main/resources")
            if (!resourcesDir.exists()) {
                resourcesDir.mkdirs()
            }

            val localizationFile = File(resourcesDir, "localization.json")
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(localizationFile, defaultData)
        } catch (e: Exception) {
            println("Can't create localization file: ${e.message}")
        }
    }

    private fun loadDefaultLocalization() {
        localizationData = getDefaultLocalizationData()
    }

    private fun getDefaultLocalizationData(): LocalizationData {
        val languages = mapOf(
            "en" to LanguageConfig("en", "English", "Segoe UI"),
            "ru" to LanguageConfig("ru", "Русский", "Segoe UI"),
            "zh" to LanguageConfig("zh", "中文", "Microsoft YaHei"),
            "de" to LanguageConfig("de", "Deutsch", "Segoe UI"),
            "it" to LanguageConfig("it", "Italiano", "Segoe UI"),
            "es" to LanguageConfig("es", "Español", "Segoe UI")
        )

        val translations = mapOf(
            "en" to mapOf(
                "app_title" to "RDR2 Photo Converter Tool",
                "window_title" to "RDR2 Photo Tool",
                "subtitle" to "Convert PRDR files to JPG images",
                "select_directory" to "Select directory with PRDR files:",
                "default_path" to "Default Path",
                "browse" to "Browse...",
                "conversion_options" to "Conversion Options:",
                "create_backup" to "Create backup of PRDR files",
                "delete_original" to "Delete original PRDR files after conversion",
                "convert_files" to "🚀 Convert Files",
                "converting" to "⏳ Converting...",
                "status_ready" to "Status: Ready to work",
                "status_default_path" to "Status: Path set to default directory",
                "status_custom_path" to "Status: Path set to selected folder",
                "status_no_folder" to "Status: No folder was selected",
                "status_valid_path" to "Status: Valid path entered",
                "status_invalid_path" to "Status: Invalid path entered",
                "no_files_found" to "No PRDR files found in the selected directory.",
                "conversion_complete" to "✅ Conversion completed successfully!",
                "files_converted" to "files converted",
                "files_converted_to_images" to "files converted to images.",
                "errors" to "Errors:",
                "files_backed_up" to "files saved to backup,",
                "duplicates" to "duplicates.",
                "found_files" to "Found",
                "prdr_files_ready" to "PRDR files, ready for conversion",
                "information" to "Information",
                "error" to "Error",
                "directory_error" to "Error setting up directories:",
                "rdr2_not_found" to "Could not find RDR2 directory. Use 'Browse' button to select path.",
                "invalid_path_message" to "Invalid path entered. Check the entered path and try again.\\n\\nExample of correct path:\\n/home/user/SomeFolder/AnotherFolder",
                "files_search_error" to "Error searching for files:",
                "language" to "Language:"
            ),
            "ru" to mapOf(
                "app_title" to "RDR2 Photo Converter Tool",
                "window_title" to "RDR2 Photo Tool",
                "subtitle" to "Конвертация PRDR файлов в JPG изображения",
                "select_directory" to "Выберите директорию с PRDR файлами:",
                "default_path" to "Путь по умолчанию",
                "browse" to "Обзор...",
                "conversion_options" to "Опции конвертации:",
                "create_backup" to "Создать резервную копию PRDR файлов",
                "delete_original" to "Удалить исходные PRDR файлы после конвертации",
                "convert_files" to "🚀 Конвертировать файлы",
                "converting" to "⏳ Конвертация...",
                "status_ready" to "Статус: Готов к работе",
                "status_default_path" to "Статус: Путь установлен в директорию по умолчанию",
                "status_custom_path" to "Статус: Путь установлен в выбранную папку",
                "status_no_folder" to "Статус: Папка не была выбрана",
                "status_valid_path" to "Статус: Введен корректный путь",
                "status_invalid_path" to "Статус: Введен некорректный путь",
                "no_files_found" to "В выбранной директории не найдено PRDR файлов.",
                "conversion_complete" to "✅ Конвертация завершена успешно!",
                "files_converted" to "файлов конвертировано",
                "files_converted_to_images" to "файлов конвертировано в изображения.",
                "errors" to "Ошибок:",
                "files_backed_up" to "файлов сохранено в резервную копию,",
                "duplicates" to "дубликатов.",
                "found_files" to "Найдено",
                "prdr_files_ready" to "PRDR файлов, готово к конвертации",
                "information" to "Информация",
                "error" to "Ошибка",
                "directory_error" to "Ошибка при настройке директорий:",
                "rdr2_not_found" to "Не удалось найти директорию RDR2. Используйте кнопку 'Обзор' для выбора пути.",
                "invalid_path_message" to "Введен некорректный путь. Проверьте введенный путь и попробуйте снова.\\n\\nПример корректного пути:\\n/home/user/SomeFolder/AnotherFolder",
                "files_search_error" to "Ошибка при поиске файлов:",
                "language" to "Язык:"
            ),
            "zh" to mapOf(
                "app_title" to "RDR2 Photo Converter Tool",
                "window_title" to "RDR2 Photo Tool",
                "subtitle" to "将PRDR文件转换为JPG图像",
                "select_directory" to "选择包含PRDR文件的目录：",
                "default_path" to "默认路径",
                "browse" to "浏览...",
                "conversion_options" to "转换选项：",
                "create_backup" to "创建PRDR文件的备份",
                "delete_original" to "转换后删除原始PRDR文件",
                "convert_files" to "🚀 转换文件",
                "converting" to "⏳ 转换中...",
                "status_ready" to "状态：准备就绪",
                "status_default_path" to "状态：路径设置为默认目录",
                "status_custom_path" to "状态：路径设置为选定文件夹",
                "status_no_folder" to "状态：未选择文件夹",
                "status_valid_path" to "状态：输入了有效路径",
                "status_invalid_path" to "状态：输入了无效路径",
                "no_files_found" to "在选定目录中未找到PRDR文件。",
                "conversion_complete" to "✅ 转换成功完成！",
                "files_converted" to "个文件已转换",
                "files_converted_to_images" to "个文件转换为图像。",
                "errors" to "错误：",
                "files_backed_up" to "个文件已备份，",
                "duplicates" to "个重复文件。",
                "found_files" to "找到",
                "prdr_files_ready" to "个PRDR文件，准备转换",
                "information" to "信息",
                "error" to "错误",
                "directory_error" to "设置目录时出错：",
                "rdr2_not_found" to "找不到RDR2目录。使用'浏览'按钮选择路径。",
                "invalid_path_message" to "输入了无效路径。请检查输入的路径并重试。\\n\\n正确路径示例：\\n/home/user/SomeFolder/AnotherFolder",
                "files_search_error" to "搜索文件时出错：",
                "language" to "语言："
            ),
            "de" to mapOf(
                "app_title" to "RDR2 Photo Converter Tool",
                "window_title" to "RDR2 Photo Tool",
                "subtitle" to "PRDR-Dateien in JPG-Bilder konvertieren",
                "select_directory" to "Wählen Sie das Verzeichnis mit PRDR-Dateien:",
                "default_path" to "Standardpfad",
                "browse" to "Durchsuchen...",
                "conversion_options" to "Konvertierungsoptionen:",
                "create_backup" to "Backup der PRDR-Dateien erstellen",
                "delete_original" to "Original-PRDR-Dateien nach Konvertierung löschen",
                "convert_files" to "🚀 Dateien konvertieren",
                "converting" to "⏳ Konvertierung...",
                "status_ready" to "Status: Bereit zur Arbeit",
                "status_default_path" to "Status: Pfad auf Standardverzeichnis gesetzt",
                "status_custom_path" to "Status: Pfad auf ausgewählten Ordner gesetzt",
                "status_no_folder" to "Status: Kein Ordner wurde ausgewählt",
                "status_valid_path" to "Status: Gültiger Pfad eingegeben",
                "status_invalid_path" to "Status: Ungültiger Pfad eingegeben",
                "no_files_found" to "Keine PRDR-Dateien im ausgewählten Verzeichnis gefunden.",
                "conversion_complete" to "✅ Konvertierung erfolgreich abgeschlossen!",
                "files_converted" to "Dateien konvertiert",
                "files_converted_to_images" to "Dateien in Bilder konvertiert.",
                "errors" to "Fehler:",
                "files_backed_up" to "Dateien gesichert,",
                "duplicates" to "Duplikate.",
                "found_files" to "Gefunden",
                "prdr_files_ready" to "PRDR-Dateien, bereit zur Konvertierung",
                "information" to "Information",
                "error" to "Fehler",
                "directory_error" to "Fehler beim Einrichten der Verzeichnisse:",
                "rdr2_not_found" to "RDR2-Verzeichnis konnte nicht gefunden werden. Verwenden Sie die Schaltfläche 'Durchsuchen', um den Pfad auszuwählen.",
                "invalid_path_message" to "Ungültiger Pfad eingegeben. Überprüfen Sie den eingegebenen Pfad und versuchen Sie es erneut.\\n\\nBeispiel für einen korrekten Pfad:\\n/home/user/SomeFolder/AnotherFolder",
                "files_search_error" to "Fehler beim Suchen nach Dateien:",
                "language" to "Sprache:"
            ),
            "it" to mapOf(
                "app_title" to "RDR2 Photo Converter Tool",
                "window_title" to "RDR2 Photo Tool",
                "subtitle" to "Converti file PRDR in immagini JPG",
                "select_directory" to "Seleziona la directory con i file PRDR:",
                "default_path" to "Percorso predefinito",
                "browse" to "Sfoglia...",
                "conversion_options" to "Opzioni di conversione:",
                "create_backup" to "Crea backup dei file PRDR",
                "delete_original" to "Elimina file PRDR originali dopo la conversione",
                "convert_files" to "🚀 Converti file",
                "converting" to "⏳ Conversione...",
                "status_ready" to "Stato: Pronto per lavorare",
                "status_default_path" to "Stato: Percorso impostato alla directory predefinita",
                "status_custom_path" to "Stato: Percorso impostato alla cartella selezionata",
                "status_no_folder" to "Stato: Nessuna cartella è stata selezionata",
                "status_valid_path" to "Stato: Percorso valido inserito",
                "status_invalid_path" to "Stato: Percorso non valido inserito",
                "no_files_found" to "Nessun file PRDR trovato nella directory selezionata.",
                "conversion_complete" to "✅ Conversione completata con successo!",
                "files_converted" to "file convertiti",
                "files_converted_to_images" to "file convertiti in immagini.",
                "errors" to "Errori:",
                "files_backed_up" to "file salvati nel backup,",
                "duplicates" to "duplicati.",
                "found_files" to "Trovati",
                "prdr_files_ready" to "file PRDR, pronti per la conversione",
                "information" to "Informazioni",
                "error" to "Errore",
                "directory_error" to "Errore nella configurazione delle directory:",
                "rdr2_not_found" to "Impossibile trovare la directory RDR2. Utilizza il pulsante 'Sfoglia' per selezionare il percorso.",
                "invalid_path_message" to "Percorso non valido inserito. Controlla il percorso inserito e riprova.\\n\\nEsempio di percorso corretto:\\n/home/user/SomeFolder/AnotherFolder",
                "files_search_error" to "Errore nella ricerca dei file:",
                "language" to "Lingua:"
            ),
            "es" to mapOf(
                "app_title" to "RDR2 Photo Converter Tool",
                "window_title" to "RDR2 Photo Tool",
                "subtitle" to "Convertir archivos PRDR a imágenes JPG",
                "select_directory" to "Seleccione el directorio con archivos PRDR:",
                "default_path" to "Ruta predeterminada",
                "browse" to "Examinar...",
                "conversion_options" to "Opciones de conversión:",
                "create_backup" to "Crear copia de seguridad de archivos PRDR",
                "delete_original" to "Eliminar archivos PRDR originales después de la conversión",
                "convert_files" to "🚀 Convertir archivos",
                "converting" to "⏳ Convirtiendo...",
                "status_ready" to "Estado: Listo para trabajar",
                "status_default_path" to "Estado: Ruta establecida al directorio predeterminado",
                "status_custom_path" to "Estado: Ruta establecida a la carpeta seleccionada",
                "status_no_folder" to "Estado: No se seleccionó ninguna carpeta",
                "status_valid_path" to "Estado: Ruta válida ingresada",
                "status_invalid_path" to "Estado: Ruta inválida ingresada",
                "no_files_found" to "No se encontraron archivos PRDR en el directorio seleccionado.",
                "conversion_complete" to "✅ ¡Conversión completada exitosamente!",
                "files_converted" to "archivos convertidos",
                "files_converted_to_images" to "archivos convertidos a imágenes.",
                "errors" to "Errores:",
                "files_backed_up" to "archivos guardados en copia de seguridad,",
                "duplicates" to "duplicados.",
                "found_files" to "Encontrados",
                "prdr_files_ready" to "archivos PRDR, listos para conversión",
                "information" to "Información",
                "error" to "Error",
                "directory_error" to "Error al configurar directorios:",
                "rdr2_not_found" to "No se pudo encontrar el directorio RDR2. Use el botón 'Examinar' para seleccionar la ruta.",
                "invalid_path_message" to "Ruta inválida ingresada. Verifique la ruta ingresada e intente nuevamente.\\n\\nEjemplo de ruta correcta:\\n/home/user/SomeFolder/AnotherFolder",
                "files_search_error" to "Error al buscar archivos:",
                "language" to "Idioma:"
            )
        )

        return LocalizationData(languages, translations)
    }

    // Get correct font for current language
    fun getFont(style: Int = Font.PLAIN, size: Int = 12): Font {
        val languageConfig = localizationData?.languages?.get(currentLanguage.code)
        val fontName = languageConfig?.fontFamily ?: when (currentLanguage) {
            Language.CHINESE -> {
                // Try to find correct chinese font
                val availableFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().availableFontFamilyNames
                when {
                    availableFonts.contains("Microsoft YaHei") -> "Microsoft YaHei"
                    availableFonts.contains("SimSun") -> "SimSun"
                    availableFonts.contains("NSimSun") -> "NSimSun"
                    availableFonts.contains("SimHei") -> "SimHei"
                    availableFonts.contains("STSong") -> "STSong"
                    availableFonts.contains("PingFang SC") -> "PingFang SC"
                    availableFonts.contains("Source Han Sans") -> "Source Han Sans"
                    else -> Font.SANS_SERIF // Fallback on system font
                }
            }
            else -> "Segoe UI"
        }

        val font = Font(fontName, style, size)

        // Check that current font can display chinese symbols
        if (currentLanguage == Language.CHINESE) {
            val testChar = '中' // Test chinese symbol
            if (!font.canDisplay(testChar)) {
                // Fallback on system font
                return Font(Font.SANS_SERIF, style, size)
            }
        }

        return font
    }

    fun setLanguage(language: Language) {
        currentLanguage = language
    }

    fun getCurrentLanguage(): Language = currentLanguage

    fun get(key: String): String {
        return localizationData?.translations?.get(currentLanguage.code)?.get(key)
            ?: localizationData?.translations?.get("en")?.get(key)
            ?: key
    }
}