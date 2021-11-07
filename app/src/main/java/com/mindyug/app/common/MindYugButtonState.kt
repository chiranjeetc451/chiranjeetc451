package com.mindyug.app.common

data class MindYugButtonState(
    val isEnabled: Boolean = true,
    val error: String? = "",
    val isLoading: Boolean = false
)
