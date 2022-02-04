package com.mindyug.app.presentation.settings

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mindyug.app.common.components.GradientButton
import com.mindyug.app.ui.theme.MindYugTheme

@Composable
fun AddressScreen(
    navHostController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel(),

    ) {
    MindYugTheme {
        Scaffold(
            topBar = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navHostController.navigateUp() },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIos,
                            contentDescription = "back",
                            tint = MaterialTheme.colors.secondary
                        )
                    }
                    Spacer(modifier = Modifier.width(115.dp))
                    Text(
//                        modifier = Modifier.fillMaxWidth(),
                        text = "Address",
                        color = Color.White,
//                        textAlign = TextAlign.Center
                    )
                }
            },
            backgroundColor = Color(0xFF0D3F56)
        ) {
            val loadingState = viewModel.accountSettingsState.value

            val phoneNumber = viewModel.phoneNumber.value
            val userName = viewModel.registeredName.value

            val save = viewModel.btnAddressSave


            val addressLineOne = viewModel.addressLineOne.value
            val addressLineTwo = viewModel.addressLineTwo.value
            val houseNo = viewModel.houseNo.value
            val landmark = viewModel.landmark.value
            val city = viewModel.city.value
            val state = viewModel.state.value
            val country = viewModel.country.value
            val pincode = viewModel.pincode.value


            val scrollState = rememberScrollState()
            if (loadingState.isLoading) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    var text by remember { mutableStateOf("") }



                    Text(
                        text = "Address Line 1:",
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = addressLineOne.text,
                        onValueChange = { viewModel.onEvent(AddressEvent.EnteredAddressLineOne(it)) },
                        placeholder = {
                            Text(
                                text = "Address Line 1",
                                Modifier.alpha(0.5f)
                            )
                        },
                        shape = RoundedCornerShape(6.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Address Line 2:",
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = addressLineTwo.text,
                        onValueChange = { viewModel.onEvent(AddressEvent.EnteredAddressLineTwo(it)) },
                        placeholder = {
                            Text(
                                text = "Address Line 2",
                                Modifier.alpha(0.5f)
                            )
                        },
                        shape = RoundedCornerShape(6.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "House No:",
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = houseNo.text,
                        onValueChange = { viewModel.onEvent(AddressEvent.EnteredHouseNo(it)) },
                        placeholder = {
                            Text(
                                text = "House No",
                                Modifier.alpha(0.5f)
                            )
                        },
                        shape = RoundedCornerShape(6.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        )
                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Landmark:",
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = landmark.text,
                        onValueChange = { viewModel.onEvent(AddressEvent.EnteredLandmark(it)) },
                        placeholder = {
                            Text(
                                text = "Landmark",
                                Modifier.alpha(0.5f)
                            )
                        },
                        shape = RoundedCornerShape(6.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Pin Code:",
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = pincode.text,
                        onValueChange = { viewModel.onEvent(AddressEvent.EnteredPinCode(it)) },
                        placeholder = {
                            Text(
                                text = "Pin Code",
                                Modifier.alpha(0.5f)
                            )
                        },
                        shape = RoundedCornerShape(6.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "City:",
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = city.text,
                        onValueChange = { viewModel.onEvent(AddressEvent.EnteredCity(it)) },
                        placeholder = {
                            Text(
                                text = "City",
                                Modifier.alpha(0.5f)
                            )
                        },
                        shape = RoundedCornerShape(6.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "State:",
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = state.text,
                        onValueChange = { viewModel.onEvent(AddressEvent.EnteredState(it)) },
                        placeholder = {
                            Text(
                                text = "State",
                                Modifier.alpha(0.5f)
                            )
                        },
                        shape = RoundedCornerShape(6.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Country",
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = country.text,
                        onValueChange = { viewModel.onEvent(AddressEvent.EnteredCountry(it)) },
                        placeholder = {
                            Text(
                                text = "Country",
                                Modifier.alpha(0.5f)
                            )
                        },
                        shape = RoundedCornerShape(6.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GradientButton(
                            onClick = {
                                viewModel.updateUserData(
                                    userName!!,
                                    phoneNumber.text!!,
                                    houseNo.text!!,
                                    addressLineOne.text,
                                    addressLineTwo.text,
                                    pincode.text,
                                    landmark.text,
                                    city.text,
                                    state.text,
                                    country.text,
                                    navHostController
                                )
                            },
                            enabled = save.value.isEnabled

                        ) {
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = "Save", color = Color.White)
                            Spacer(modifier = Modifier.width(16.dp))

                        }
                    }
                }
            }
        }
    }
}