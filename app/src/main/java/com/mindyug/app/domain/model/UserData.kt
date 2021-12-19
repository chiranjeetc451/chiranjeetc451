package com.mindyug.app.domain.model

data class UserData(
    val name: String,
    val number: String,
    val address: Address?
){
    constructor() : this("", "", null)

}