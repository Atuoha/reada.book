package com.example.readers_app.presentation.screens.home.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.readers_app.R
import com.example.readers_app.ui.theme.primary
import kotlinx.coroutines.delay

@Composable
fun CarouselComponent() {
    val carouselImages = listOf(
        R.drawable.banner1,
        R.drawable.banner2,
        R.drawable.banner4,
        R.drawable.banner3,
        R.drawable.banner5,
        )
    val pagerState = rememberPagerState(pageCount = { carouselImages.size })

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % carouselImages.size
            pagerState.animateScrollToPage(nextPage)
        }
    }
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.padding(start = 16.dp),
        contentPadding = PaddingValues(end = 15.dp)
    ) { page ->
        CarouselImage(item = carouselImages[page])
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.Center

    ) {
        repeat(carouselImages.size) { index ->
            val color = if (pagerState.currentPage == index) primary else Color.LightGray
            val width = if (pagerState.currentPage == index) 16.dp else 5.dp

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .height(3.dp)
                    .width(width)
                    .background(color = color, shape = CircleShape)
            )
        }
    }
}