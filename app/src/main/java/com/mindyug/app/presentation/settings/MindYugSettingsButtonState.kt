package com.mindyug.app.presentation.settings

data class MindYugSettingsButtonState(
    val isEnabled: Boolean = false,
    val error: String? = "",
    val isLoading: Boolean = false
)
