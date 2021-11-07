package com.mindyug.app.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindyug.app.domain.model.PointItem
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PointsList(
    list: MutableList<PointItem>
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "History",
            color = Color.White,
            style = MaterialTheme.typography.caption
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colors.primary)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(list.size) {
                PointListItem(pointItem = list[it])
            }

        }
    }


}

@Composable
fun PointListItem(
    pointItem: PointItem
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(percent = 50))
            .background(Color(0xFF0D3F56))
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {
        Text(text = getDateAsStringFromDate(pointItem.date), color = Color.White)
        Text(text = pointItem.points.toString(), color = if(pointItem.points>0){ Color(0xFF2CE07F)} else  { Color.Red })
    }
    Spacer(modifier = Modifier.height(16.dp))
}

fun getDateAsStringFromDate(date: Date): String {
    val pattern = "dd MMM, YYYY"
    val df: DateFormat = SimpleDateFormat(pattern)
    return df.format(date)
}

@Preview
@Composable
fun PointItemPreview() {
    PointListItem(PointItem(Date(), 90000))
}