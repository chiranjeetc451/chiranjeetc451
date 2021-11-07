package com.mindyug.app.presentation.settings

data class AddressState(
    val houseNo: String? = "",
    val addressLineOne: String = "",
    val addressLineTwo: String? = "",
    val pinCode: String = "",
    val landmark: String? = "",
    val city: String = "",
    val state: String = "",
    val country: String = ""
)
