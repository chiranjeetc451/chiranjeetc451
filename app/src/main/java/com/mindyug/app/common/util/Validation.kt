package com.mindyug.app.common.util

import java.util.regex.Pattern

 fun validateNumber(text: String): Boolean {
    val reg = "[6-9]{1}[0-9]{9}"
    val pattern: Pattern = Pattern.compile(reg)
    return pattern.matcher(text).find() && text.isNotEmpty()
}

 fun validateName(text: String): Boolean {
    val reg = "^[a-zA-Z]{1,}(?: [a-zA-Z]+){0,5}\$"
    val pattern: Pattern = Pattern.compile(reg)
    return pattern.matcher(text).find() && text.isNotEmpty()
}


fun validateUsername(text: String): Boolean {
    val reg = "^([A-Za-z0-9_](?:(?:[A-Za-z0-9_]|(?:\\.(?!\\.))){0,28}(?:[A-Za-z0-9_]))?)\$"
    val pattern: Pattern = Pattern.compile(reg)
    return pattern.matcher(text).find() && text.isNotEmpty()
}