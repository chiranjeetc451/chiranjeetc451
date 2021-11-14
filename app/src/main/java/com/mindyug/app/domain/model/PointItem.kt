package com.mindyug.app.domain.model

data class PointItem(
    val date: String,
    val month: String,
    val year: String,
    val fullDate: String,
    val points: Long
) {
    constructor() : this("", "", "", "", 0)
}