package com.megaomegapepe.rdr2.config.localization

data class LocalizationData(
    val languages: Map<String, LanguageConfig>,
    val translations: Map<String, Map<String, String>>
)