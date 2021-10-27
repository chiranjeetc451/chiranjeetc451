package com.mindyug.app.presentation.home

import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mindyug.app.R
import com.mindyug.app.presentation.home.components.MindYugBottomNavigation
import com.mindyug.app.presentation.home.components.MindYugBottomNavigationItem
import com.mindyug.app.presentation.home.components.PointKeeper
import com.mindyug.app.ui.theme.MindYugTheme




@Composable
fun MindYugBottomNavigationBar(navController: NavController) {
    MindYugTheme {
        MindYugBottomNavigation(
            backgroundColor = Color(0xFF0D3F56),
            contentColor = Color.White,
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val score = "1000"
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_home),
                        contentDescription = stringResource(R.string.dashboard)
                    )
                },
                selectedContentColor = MaterialTheme.colors.secondary,
                unselectedContentColor = Color.White,
                alwaysShowLabel = false,
                selected = currentRoute == "home/dashboard",
                onClick = {
                    navController.navigate("home/dashboard") {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )


            MindYugBottomNavigationItem(content = {
                PointKeeper(
                    color = Color(0xFF002333),
                    score = score
                )
            })
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_trophy),
                        contentDescription = stringResource(id = R.string.rewards)
                    )
//                           Text(text =stringResource(id = item.title) )
                },
                selectedContentColor = MaterialTheme.colors.secondary,
                unselectedContentColor = Color.White,
                alwaysShowLabel = false,
                selected = currentRoute == "home/rewards",
                onClick = {
                    navController.navigate("home/rewards") {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )

        }
    }
}


