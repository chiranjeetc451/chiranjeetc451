package com.mindyug.app.presentation.dashboard

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mindyug.app.R
import com.mindyug.app.common.util.getDurationBreakdown
import com.mindyug.app.domain.model.AppStat
import com.mindyug.app.presentation.dashboard.components.AnimatedCircle
import com.mindyug.app.presentation.dashboard.components.ColouredSection
import com.mindyug.app.presentation.dashboard.components.MindYugStatCard
import com.mindyug.app.presentation.dashboard.components.TempPointKeeper
import com.mindyug.app.presentation.util.Screen
import kotlinx.coroutines.delay
import java.util.*

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun Dashboard(
    viewModel: DashboardViewModel = hiltViewModel(),
    navController: NavHostController,
    temporaryPoints: Long,
    list: MutableList<AppStat>
) {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("userLoginState", MODE_PRIVATE)
    val uid = sharedPref.getString("uid", null)

    val listState = viewModel.appListGrid.value

    LaunchedEffect(Unit) {
        viewModel.getProfilePictureUri(uid!!)
        viewModel.getStatData(Date())
    }

    val imageUri = viewModel.profilePictureUri.value.uri


    SwipeRefresh(
        state = viewModel.refreshState.value, onRefresh = {
            viewModel.delayFun()
            viewModel.getProfilePictureUri(uid!!)
            viewModel.getStatData(Date())
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            item {
                TopBar(
                    imageUri = imageUri,
                    navController = navController,
                    temporaryPoints = temporaryPoints
                )
            }
            item {
                val abc = mutableListOf<AppStat>(
                    AppStat("com.whatsapp", 778),
                    AppStat("com.whatsapp", 778),
                    AppStat("com.whatsapp", 778),
                    AppStat("com.whatsapp", 778),
                    AppStat("com.whatsapp", 778),
                    AppStat("com.whatsapp", 778)
                )

                Graph(totalTimeInMillis = 200000, list = abc, context = context)
            }

            if (!listState.isLoading) {
                list.sortByDescending { it.foregroundTime }

                list.distinctBy { it.packageName }
                for (app in list){
                    Log.d("tag", app.packageName)
                }
                items(list.windowed(2, 2, true)) { sublist ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
//                    .padding(8.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            sublist.forEach { item ->
                                MindYugStatCard(
                                    context,
                                    item
                                )
                            }


//                AppStatGridList(list, context)

                        }
                    }
                }
            } else {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(30.dp),
                            strokeWidth = 3.dp,

                            color = Color.White
                        )
                    }
                }

            }
        }
    }


}


@Composable
fun Graph(
    totalTimeInMillis: Long,
    list: MutableList<AppStat>,
    context: Context

) {
//    var time = totalTimeInMillis
//    val fiveList = mutableListOf<AppStat>()
//
//    for (i in 0..4) {
//        fiveList.add(list[i])
//    }
//
//    list.removeAt(0)
//    list.removeAt(1)
//    list.removeAt(2)
//    list.removeAt(3)
//    list.removeAt(4)

//    LaunchedEffect(Unit) {
//        for (app in fiveList) {
//            Log.d("taggedabcc", app.foregroundTime.toString())
//        }
//    }
//
//    val lastElement = AppStat("Others", list.sumOf { it.foregroundTime })
//    fiveList.add(lastElement)
//
//
//    val abc = mutableListOf<Float>()
//
//    fiveList.forEach() {
//        abc.add(
//            if (it.foregroundTime.toFloat().equals(0f)) {
//                0f
//            } else {
//                it.foregroundTime.toFloat() / totalTimeInMillis
//            }
//        )
//    }


    Box(
        Modifier
            .padding(16.dp)
    ) {
//        val bcd = mutableListOf(
//            0f,
//            0f,
//            0f,
//            0f,
//            0f,
//            1f
//        )
        AnimatedCircle(
            proportions = mutableListOf(
                0.2f,
                0.3f,
                0.2f,
                0.1f,
                0.1f,
                01f
            ),
//            if (
//                abc[0].equals(0f) &&
//                abc[1].equals(0f) &&
//                abc[2].equals(0f) &&
//                abc[3].equals(0f) &&
//                abc[4].equals(0f) &&
//                abc[5].equals(0f)
//            ) {
//                bcd
//            } else {
//                bcd
//            },
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ColouredSection(context, list)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Total Time:",
                style = MaterialTheme.typography.caption,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = getDurationBreakdown(totalTimeInMillis)!!,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }


}

@ExperimentalCoilApi
@Composable
fun TopBar(
    imageUri: Uri?,
    navController: NavHostController,
    temporaryPoints: Long

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
            Spacer(modifier = Modifier.width(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TempPointKeeper(points = temporaryPoints)
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
                onClick = { navController.navigate(Screen.NotificationsScreen.route) }
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
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape),
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
fun AppStatGridList(list: MutableList<AppStat>, context: Context) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 72.dp),
    ) {
        items(list.size) {
            MindYugStatCard(
                context,
                list[it]
            )
        }

    }

}






