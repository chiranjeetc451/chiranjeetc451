package com.mindyug.app.presentation.profile

import com.mindyug.app.domain.model.StatData

data class StatDataList(
    val isLoading: Boolean = true,
    val list: List<StatData> = emptyList(),
)
