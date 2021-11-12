package com.mindyug.app.presentation.settings

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.mindyug.app.presentation.settings.components.SettingsButton
import com.mindyug.app.presentation.util.Screen
import com.mindyug.app.ui.theme.MindYugTheme

@Composable
fun SettingsScreen(
    navHostController: NavHostController
) {
    MindYugTheme {
        Scaffold(
            topBar = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navHostController.navigate(Screen.ProfileScreen.route) },
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
                        text = "Settings",
                        color = Color.White,
//                        textAlign = TextAlign.Center
                    )
                }
            },
            backgroundColor = Color(0xFF0D3F56)
        ) {
            val context = LocalContext.current

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 72.dp, end = 72.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                SettingsButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        navHostController.navigate(Screen.AccountSettings.route)
                    }
                ) {
                    Text(
                        text = "Account Settings",
                        color = Color.White,
                        style = MaterialTheme.typography.body1
                    )

                }
                Spacer(modifier = Modifier.height(16.dp))
                SettingsButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        navHostController.navigate(Screen.Address.route)
                    },

                    ) {
                    Text(
                        text = "Address",
                        color = Color.White,
                        style = MaterialTheme.typography.body1

                    )

                }
                Spacer(modifier = Modifier.height(16.dp))
                SettingsButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        text = "Privacy Policy",
                        color = Color.White,
                        style = MaterialTheme.typography.body1

                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                SettingsButton(
                    modifier = Modifier.fillMaxWidth(),

                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        text = "Terms of Service",
                        color = Color.White,
                        style = MaterialTheme.typography.body1

                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                SettingsButton(
                    modifier = Modifier.fillMaxWidth(),

                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        text = "FAQs",
                        color = Color.White,
                        style = MaterialTheme.typography.body1
                    )

                }
                Spacer(modifier = Modifier.height(16.dp))
                SettingsButton(
                    modifier = Modifier.fillMaxWidth(),

                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        text = "Contact Us",
                        color = Color.White,
                        style = MaterialTheme.typography.body1
                    )
                }
                Spacer(modifier = Modifier.height(38.dp))
                SettingsButton(
                    onClick = {
                        FirebaseAuth.getInstance().signOut()
                        val sharedPref =
                            context.getSharedPreferences("userLoginState", Context.MODE_PRIVATE)
                        sharedPref.edit().clear().apply()
                        navHostController.navigate(Screen.IntroductionScreen.route)
                    }
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Log Out",
                        color = Color.White,
                        style = MaterialTheme.typography.body1
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}