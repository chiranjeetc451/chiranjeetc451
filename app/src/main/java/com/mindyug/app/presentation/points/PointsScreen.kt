package com.mindyug.app.presentation.points

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mindyug.app.common.components.PointKeeper
import com.mindyug.app.common.components.PointsList
import com.mindyug.app.common.components.getDateAsStringFromDate
import com.mindyug.app.common.util.getDateFromDate
import com.mindyug.app.common.util.getMonthFromDate
import com.mindyug.app.common.util.getPrimaryKeyDate
import com.mindyug.app.common.util.getYearFromDate
import com.mindyug.app.domain.model.PointItem
import com.mindyug.app.presentation.home.MindYugBottomNavigationBar
import com.mindyug.app.presentation.login.LoginViewModel
import com.mindyug.app.presentation.points.components.CollectSection
import com.mindyug.app.ui.theme.MindYugTheme
import java.util.*

@Composable
fun PointsScreen(
    viewModel: PointViewModel = hiltViewModel(),
    navController: NavHostController,
    elevation: Dp,
    isEnabled: Boolean,
    temporaryPoints: Long
) {
    MindYugTheme {
        val context = LocalContext.current
        val sharedPref =
            context.getSharedPreferences("userLoginState", Context.MODE_PRIVATE)
        val uid = sharedPref.getString("uid", null)

        Scaffold() {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF0D3F56)),
//                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LaunchedEffect(Unit) {
                    viewModel.getPoints(uid!!, context)
                }

                val ab = viewModel.pointList.value


                MindYugBottomNavigationBar(
                    navController = navController,
                    elevation = elevation,
                    isEnabled = isEnabled,
                    points = ab.points,
                    isLoading = ab.isLoading
                )

                val date = Date()

                Spacer(modifier = Modifier.height(8.dp))
                CollectSection(points = temporaryPoints, onClick =
                {
                    viewModel.addPoint(
                        PointItem(
                            getDateFromDate(Date()),
                            getMonthFromDate(Date()),
                            getYearFromDate(Date()),
                            getPrimaryKeyDate(Date()),
                            temporaryPoints
                        ), uid!!, context
                    )
                    viewModel.getPoints(uid!!, context)

                }
                )
                Spacer(modifier = Modifier.height(8.dp))


                PointsList(ab.list, ab.isLoading)

            }

        }
    }
}