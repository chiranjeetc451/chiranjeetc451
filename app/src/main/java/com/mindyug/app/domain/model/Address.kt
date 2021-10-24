package com.mindyug.app.domain.model

data class Address(
    val houseNo: String?,
    val addressLineOne: String,
    val addressLineTwo: String?,
    val pinCode: String,
    val landmark: String?,
    val city: String,
    val state: String,
    val country: String
)