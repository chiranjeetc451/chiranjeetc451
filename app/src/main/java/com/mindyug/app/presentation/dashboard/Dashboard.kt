package com.mindyug.app.presentation.dashboard

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.mindyug.app.R
import com.mindyug.app.domain.model.AppStat
import com.mindyug.app.presentation.dashboard.components.AnimatedCircle
import com.mindyug.app.presentation.dashboard.components.MindYugStatCard
import com.mindyug.app.presentation.util.Screen

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun Dashboard(
    viewModel: DashboardViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("userLoginState", MODE_PRIVATE)
    val uid = sharedPref.getString("uid", null)

    LaunchedEffect(Unit) {
        viewModel.getProfilePictureUri(uid!!)
    }

    val imageUri = viewModel.profilePictureUri.value.uri

    Column {
        TopBar(
            imageUri = imageUri,
            navController = navController
        )
        Box(
            Modifier
                .padding(16.dp)
        ) {
            val abc = listOf(0.1f, 0.1f, 0.3f, 0.15f, 0.05f, 0.3f)
            AnimatedCircle(
                proportions = abc,
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
            )

            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = "Total Time:",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "3 Hours",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

//        val numbers = mutableListOf(AppStat("com.whatsapp", 6799999), AppStat("com.android.chrome",565776))

        AppStatGridList(numbers, context)
    }


}

@ExperimentalCoilApi
@Composable
fun TopBar(
    imageUri: Uri?,
    navController: NavHostController
) {
    var showMenu by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            IconButton(
                onClick = { showMenu = !showMenu }
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "menu"
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
                modifier = Modifier.background(Color(0xFF0D3F56))
            ) {
                Text(
                    text = "Menu",
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.Center
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = Modifier.clip(RoundedCornerShape(percent = 50)),
                        onClick = { /*TODO*/ }
                    ) {
                        Spacer(modifier = Modifier.width(23.dp))
                        Text(
                            text = "Refer a friend",
                            style = MaterialTheme.typography.caption
                        )
                        Spacer(modifier = Modifier.width(23.dp))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        modifier = Modifier.clip(RoundedCornerShape(percent = 50)),
                        onClick = { /*TODO*/ }
                    ) {
                        Spacer(modifier = Modifier.width(24.dp))
                        Text(
                            text = "Leaderboard",
                            style = MaterialTheme.typography.caption
                        )
                        Spacer(modifier = Modifier.width(24.dp))

                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        modifier = Modifier.clip(RoundedCornerShape(percent = 50)),
                        onClick = { /*TODO*/ }
                    ) {
                        Spacer(modifier = Modifier.width(30.dp))
                        Text(
                            text = "Go Pro",
                            style = MaterialTheme.typography.caption
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Image(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.crown),
                            contentDescription = "crown"
                        )
                        Spacer(modifier = Modifier.width(30.dp))

                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        modifier = Modifier.clip(RoundedCornerShape(percent = 50)),
                        onClick = { /*TODO*/ }
                    ) {
                        Spacer(modifier = Modifier.width(24.dp))
                        Text(
                            text = "Live Support",
                            style = MaterialTheme.typography.caption
                        )
                        Spacer(modifier = Modifier.width(24.dp))

                    }

                }

            }


        },
        actions = {

            val painter = rememberImagePainter(
                data = imageUri, builder = {
                    transformations(
                        CircleCropTransformation()
                    )
                    placeholder(R.drawable.ic_profile_pic)
                    error(R.drawable.ic_profile_pic)
                })



            IconButton(
                onClick = { /* TODO: Open notifications */ }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "notifications"
                )
            }
            IconButton(
                onClick = {
                    navController.navigate(Screen.ProfileScreen.route)
                }
            ) {
//                Icon(
//                    imageVector = Icons.Default.AccountCircle,
//                    contentDescription = "account"
//                )
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painter,
                    contentDescription = "Display picture"
                )
            }
        },
        elevation = 0.dp
    )
}

@ExperimentalFoundationApi
@Composable
fun AppStatGridList(list: List<AppStat>, context:Context) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier.padding(16.dp),
    ) {
        items(list.size) {
            MindYugStatCard(
                context,
                list[it]
            )
        }
    }

}





