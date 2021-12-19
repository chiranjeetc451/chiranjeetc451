package com.mindyug.app.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mindyug.app.common.util.getDurationBreakdown
import com.mindyug.app.domain.model.PointItem
import com.mindyug.app.domain.model.StatData
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun StatsList(
    list: List<StatData>,
    isLoading: Boolean
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Statistics History",
            color = Color.White,
            style = MaterialTheme.typography.caption
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (isLoading) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colors.primary)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colors.primary)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(list.size) {
                    StatListItem(statDataItem = list[it])
                }

            }
        }


    }


}

@Composable
fun StatListItem(
    statDataItem: StatData
) {
    val time = statDataItem.dailyUsedAppStatsList.sumOf { it.foregroundTime }

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(percent = 50))
            .background(Color(0xFF0D3F56))
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {
        Text(text = statDataItem.loggedDate, color = Color.White)
        Text(
            text = getDurationBreakdown(time)!!, color = Color.White
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

fun getDateAsStringFromDate(date: Date): String {
    val pattern = "dd MMM, YYYY"
    val df: DateFormat = SimpleDateFormat(pattern)
    return df.format(date)
}