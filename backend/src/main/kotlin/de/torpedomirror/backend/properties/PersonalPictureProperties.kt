package de.torpedomirror.backend.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "torpedomirror.personal-picture")
data class PersonalPictureProperties(
    val moduleName: String,
    val directory: String
)
