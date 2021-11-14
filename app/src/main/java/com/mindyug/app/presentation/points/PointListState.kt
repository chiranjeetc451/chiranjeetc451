package com.mindyug.app.presentation.points

import com.mindyug.app.domain.model.PointItem

data class PointListState(
    val isLoading: Boolean = true,
    val list: List<PointItem> = emptyList(),
    val points: Long = 0
)
