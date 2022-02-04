package com.mindyug.app.common

data class MindYugButtonState(
    var isEnabled: Boolean = true,
    var error: String? = "",
    var isLoading: Boolean = false
)
