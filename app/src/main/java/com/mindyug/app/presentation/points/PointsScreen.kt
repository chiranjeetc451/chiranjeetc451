package com.mindyug.app.presentation.points

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mindyug.app.common.util.monthFromDateInString
import com.mindyug.app.domain.model.PointItem
import com.mindyug.app.presentation.home.MindYugBottomNavigationBar
import com.mindyug.app.presentation.points.components.CollectSection
import com.mindyug.app.presentation.points.components.PointsList
import com.mindyug.app.ui.theme.MindYugTheme
import com.mindyug.app.utils.PointsReceiver

@Composable
fun PointsScreen(
    viewModel: PointViewModel = hiltViewModel(),
    navController: NavHostController,
    elevation: Dp,
    isEnabled: Boolean,
) {
    MindYugTheme {

        val context = LocalContext.current
        val btnState = viewModel.collectBtn.value

        val cal = Calendar.getInstance()

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


        val temporaryPoints by viewModel.temporaryPoints

        Scaffold() {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF0D3F56)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val ab = viewModel.pointList.value

                MindYugBottomNavigationBar(
                    navController = navController,
                    elevation = elevation,
                    isEnabled = isEnabled,
                    points = ab.points,
                    isLoading = ab.isLoading
                )

                Spacer(modifier = Modifier.height(8.dp))
                CollectSection(
                    points = temporaryPoints,
                    onClick = {
                        viewModel.addPoint(
                            PointItem(
                                cal.get(Calendar.DATE).toString(),
                                cal.get(Calendar.MONTH).toString(),
                                cal.get(Calendar.YEAR).toString(),
                                "${cal.get(Calendar.DATE).toString()} ${monthFromDateInString()}, ${cal.get(
                                    Calendar.YEAR).toString()}",
                                temporaryPoints
                            ),
                            context
                        )
                        viewModel.loadPoints()
                        viewModel.toggleButtonState()
                        viewModel.clear()

                        val cal = Calendar.getInstance()
                        cal[Calendar.HOUR_OF_DAY] = 23
                        cal[Calendar.MINUTE] = 59
                        cal[Calendar.SECOND] = 0

                        val intent = Intent(context, PointsReceiver::class.java)
                        val requestCode = 991
                        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)

                        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent)

                    },

                    enabled = btnState.isEnabled
//                enabled = true
                )
                Spacer(modifier = Modifier.height(8.dp))

                PointsList(ab.list, ab.isLoading)
            }
        }
    }
}





