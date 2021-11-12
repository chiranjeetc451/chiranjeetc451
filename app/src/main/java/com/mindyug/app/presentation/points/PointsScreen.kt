package com.mindyug.app.presentation.points

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mindyug.app.common.components.PointKeeper
import com.mindyug.app.common.components.PointsList
import com.mindyug.app.common.components.getDateAsStringFromDate
import com.mindyug.app.domain.model.PointItem
import com.mindyug.app.presentation.home.MindYugBottomNavigationBar
import com.mindyug.app.presentation.points.components.CollectSection
import com.mindyug.app.ui.theme.MindYugTheme
import java.util.*

@Composable
fun PointsScreen(
    navController: NavHostController,
    elevation: Dp,
    isEnabled: Boolean,
) {
    MindYugTheme {
        Scaffold() {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF0D3F56)),
//                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                MindYugBottomNavigationBar(
                    navController = navController,
                    elevation = elevation,
                    isEnabled = isEnabled
                )

                Spacer(modifier = Modifier.height(8.dp))
                CollectSection(points = 800, onClick = {

                })
                Spacer(modifier = Modifier.height(8.dp))

                val ab = mutableListOf(
                    PointItem(getDateAsStringFromDate(Date()), "ss", "sss", "sss", -90000),
                    PointItem(
                        getDateAsStringFromDate(Date()), "ss", "sss", "sss", 97000
                    ),
                    PointItem(
                        getDateAsStringFromDate(Date()), "ss", "sss", "sss", 80000
                    )
                )

                PointsList(ab)

            }

        }
    }
}