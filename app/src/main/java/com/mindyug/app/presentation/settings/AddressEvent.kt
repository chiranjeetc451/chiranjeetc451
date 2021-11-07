package com.mindyug.app.presentation.settings

sealed class AddressEvent {
    data class EnteredAddressLineOne(val value: String) : AddressEvent()
    data class EnteredAddressLineTwo(val value: String) : AddressEvent()
    data class EnteredHouseNo(val value: String) : AddressEvent()
    data class EnteredLandmark(val value: String) : AddressEvent()
    data class EnteredCity(val value: String) : AddressEvent()
    data class EnteredState(val value: String) : AddressEvent()
    data class EnteredCountry(val value: String) : AddressEvent()
    data class EnteredPinCode(val value: String) : AddressEvent()

}