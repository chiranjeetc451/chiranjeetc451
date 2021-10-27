package com.mindyug.app.presentation.introduction

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.mindyug.app.R
import com.mindyug.app.common.components.GradientButton
import com.mindyug.app.presentation.util.Screen
import com.mindyug.app.ui.theme.MindYugTheme
import com.mindyug.app.ui.theme.Typography


@Composable
fun IntroductionScreen(
    navController: NavHostController
) {
    MindYugTheme {
        PagerContent(navController = navController)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerContent(
    navController: NavHostController
) {
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val annotatedText = buildAnnotatedString {
                append(stringResource(id = R.string.agreement_half))
                pushStringAnnotation(
                    tag = "terms",// provide tag which will then be provided when you click the text
                    annotation = "terms"
                )
                withStyle(style = SpanStyle(color = Color(0xFFB5B4ED))) {
                    append(stringResource(id = R.string.terms))
                }
                append(stringResource(id = R.string.and))
                withStyle(style = SpanStyle(color = Color(0xFFB5B4ED))) {
                    append(stringResource(id = R.string.privacy_policy))
                }
                pop()
            }
            val imgList: List<Int> =
                listOf(
                    R.drawable.intro,
                    R.drawable.intro_2,
                    R.drawable.intro_3
                )
            val txtList: List<Int> =
                listOf(
                    R.string.collect_points,
                    R.string.claim_rewards,
                    R.string.increase_productivity
                )

            val pagerState = rememberPagerState(3)
            HorizontalPager(
                state = pagerState,
            ) { page ->
                PagerItem(page = page, imgList = imgList, txtList = txtList)
            }
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
            GradientButton(onClick = {
                navController.navigate(Screen.EnterNumberScreen.route)
            }, modifier = Modifier.padding(8.dp)) {
                Spacer(modifier = Modifier.width(32.dp))
                Text(text = stringResource(id = R.string.sign_in))
                Spacer(modifier = Modifier.width(32.dp))

            }
            Spacer(modifier = Modifier.height(16.dp))
            ClickableText(text = annotatedText, Modifier
                .size(250.dp, 50.dp), style = Typography.subtitle1, onClick = { offset ->
                annotatedText.getStringAnnotations(
                    tag = "terms",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    /*TODO: 2. Open Privacy Policy */
                }
            })

            Spacer(modifier = Modifier.height(8.dp))
            ClickableText(
                text = buildAnnotatedString { append(stringResource(id = R.string.problem_login)) },
                style = Typography.subtitle1,
                onClick = {
                    /*
                    TODO: 3. Problem Logging In
                     */

                })


        }
    }
}


@Composable
fun PagerItem(
    page: Int,
    imgList: List<Int>,
    txtList: List<Int>

) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = imgList[page]),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(txtList[page]), style = Typography.h5
        )
    }

}
/* TODO: 4. Deal with Orientation Changes. */





