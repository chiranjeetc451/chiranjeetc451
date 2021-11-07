package com.mindyug.app.presentation.rewards

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.*
import com.mindyug.app.R
import com.mindyug.app.presentation.rewards.components.MinYugRewardHeader
import com.mindyug.app.presentation.rewards.components.MindYugRewardCard
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun Rewards() {
    Scaffold {
        Column {
            val pagerState = rememberPagerState(pageCount = 2)
            Tabs(pagerState = pagerState)
            Spacer(modifier = Modifier.height(8.dp))
            TabsContent(pagerState = pagerState)
        }
    }

}

@Composable
fun MegaRewardsContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Coming soon!",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf("Rewards", "Mega")
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.White,
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 0.dp,
                color = Color.White
            )
        },
        modifier = Modifier
            .padding(top = 32.dp, start = 56.dp, end = 56.dp)
            .clip(RoundedCornerShape(percent = 50))
            .shadow(8.dp)
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                text = {
                    Text(
                        list[index],
                        color = if (pagerState.currentPage == index) Color.White else Color.Black
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },

                modifier = Modifier
                    .clip(RoundedCornerShape(percent = 50))
                    .background(if (pagerState.currentPage == index) Color(0xFF000022) else Color.White)
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState) {
    val state = rememberScrollState()

    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> RewardsContent(state)
            1 -> MegaRewardsContent()
        }
    }

}

@Composable
fun RewardsContent(state: ScrollState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state)
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .shadow(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF2CE07F),
                            Color(0xA62CE07F),
                        )
                    )
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(24.dp))
            Column {
                Spacer(modifier = Modifier.height(32.dp))
                Text(

                    text = "Get iPhone 12 for\nthis week.",
                    style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Medium,
                        fontSize = 24.sp
                    )
                )
                Spacer(modifier = Modifier.height(32.dp))

                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_shop),
                        contentDescription = "shopping bag",
                        tint = Color(0xFF002333)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "100,000",
                        color = Color(0xFF002333)
                    )

                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Image(
                    painter = painterResource(id = R.drawable.iphone),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )
            }
        }
        Column(modifier = Modifier.padding(bottom = 16.dp)) {
            MinYugRewardHeader(label = "Vouchers")
            Row(modifier = Modifier.padding(start = 24.dp, end = 24.dp)) {
                MindYugRewardCard(
                    modifier = Modifier.weight(0.5f),
                    label = "60% Off!",
                    brush = Brush.linearGradient(
                        listOf(
                            Color(0xFFEB92ED),
                            Color(0xB3EB92ED),
                        )
                    ),
                    id = R.drawable.myntra
                )
                Spacer(modifier = Modifier.width(16.dp))
                MindYugRewardCard(
                    modifier = Modifier.weight(0.5f),
                    label = "35% Off!",
                    brush = Brush.linearGradient(
                        listOf(
                            Color(0xFFFF702B),
                            Color(0xCCFF702B),
                        )
                    ),
                    id = R.drawable.swiggy

                )

            }
        }
        Column(modifier = Modifier.padding(bottom = 16.dp)) {
            MinYugRewardHeader(label = "Money")
            Row(modifier = Modifier.padding(start = 24.dp, end = 24.dp)) {
                MindYugRewardCard(
                    modifier = Modifier.weight(0.5f),
                    label = "₹1000",
                    brush = Brush.linearGradient(
                        listOf(
                            Color(0xFF7FFFD4),
                            Color(0x8C7FFFD4),
                        )
                    ),
                    id = R.drawable.btc
                )
                Spacer(modifier = Modifier.width(16.dp))
                MindYugRewardCard(
                    modifier = Modifier.weight(0.5f),
                    label = "₹5000",
                    brush = Brush.linearGradient(
                        listOf(
                            Color(0xFF416CFF),
                            Color(0x4D416CFF),
                        )
                    ),
                    id = R.drawable.paytm

                )

            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.padding(start = 24.dp, end = 24.dp)) {
                MindYugRewardCard(
                    modifier = Modifier.weight(0.5f),
                    label = "₹7000",
                    brush = Brush.linearGradient(
                        listOf(
                            Color(0xFFF7E72F),
                            Color(0x99F7E72F),
                        )
                    ),
                    id = R.drawable.flipkart
                )
                Spacer(modifier = Modifier.width(16.dp))
                MindYugRewardCard(
                    modifier = Modifier.weight(0.5f),
                    label = "₹10000",
                    brush = Brush.linearGradient(
                        listOf(
                            Color(0xFFFFFFFF),
                            Color(0x80CEC8BF),
                        )
                    ),
                    id = R.drawable.amazon

                )

            }

        }
        Column(modifier = Modifier.padding(bottom = 16.dp)) {
            Row(modifier = Modifier.padding(top = 8.dp, start = 24.dp, end = 24.dp)) {
                Text(
                    modifier = Modifier.weight(0.5f),
                    text = "Weekly Rewards",
                    textAlign = TextAlign.Start
                )
                Row(
                    modifier = Modifier
                        .weight(0.5f),
                    horizontalArrangement = Arrangement.End

                ) {
                    Row {
                        Text(
                            text = "Upcoming",
                            textAlign = TextAlign.End,
                        )
                    }
                }


            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .shadow(8.dp)
                        .size(220.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Color(0xFF66CDA1),
                                    Color(0xFF36BFE3),
                                )
                            )
                        )

                        .clickable { }
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(140.dp),
                        painter = painterResource(id = R.drawable.iphone),
                        contentDescription = null
                    )
                    Text(
                        text = "iPhone 12",
                        style = MaterialTheme.typography.h6
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Icon(imageVector = Icons.Filled.Lock, contentDescription = null)
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .shadow(8.dp)
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        Color(0xFFFF000C),
                                        Color(0xB3FF000C),
                                    )
                                )
                            )
                            .clickable { }
                            .padding(8.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier.size(85.dp),
                            painter = painterResource(id = R.drawable.watch),
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        modifier = Modifier
                            .shadow(8.dp)
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        Color(0xFFA2DDE0),
                                        Color(0x99A2DDE0),
                                    )
                                )
                            )
                            .clickable { }
                            .padding(8.dp), verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier.size(85.dp),
                            painter = painterResource(id = R.drawable.shoes),
                            contentDescription = null
                        )
                    }


                }


            }

        }
        Column(modifier = Modifier.padding(bottom = 8.dp)) {
            MinYugRewardHeader(label = "Subscriptions")
            Spacer(modifier = Modifier.height(1.dp))

            Column(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp)
                    .shadow(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        Color.White
                    )
                    .clickable {  },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Image(
                        modifier = Modifier.size(120.dp),
                        painter = painterResource(id = R.drawable.skillshare),
                        contentDescription = null
                    )
                    Text(text = "Get 12 months of free subscription", color = Color.Black)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }

            }


        }

        Spacer(modifier = Modifier.height(72.dp))

    }

}

@Preview
@Composable
fun Content() {
    val state = rememberScrollState()
    RewardsContent(state)
}

