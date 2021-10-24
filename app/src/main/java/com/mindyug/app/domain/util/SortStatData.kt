package com.mindyug.app.domain.util

sealed class SortStatData {
    object Monthly : SortStatData()
    object Daily : SortStatData()
    object Yearly : SortStatData()
}