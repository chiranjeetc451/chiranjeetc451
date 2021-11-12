package com.mindyug.app.presentation.dashboard

import com.mindyug.app.domain.model.AppStat

data class AppListGrid(
    val isLoading: Boolean = true,
    val list: MutableList<AppStat>? = null
)