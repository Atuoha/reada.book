package com.example.readers_app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readers_app.ui.theme.primary

@Composable
fun CategorySection(
    categories: List<String>,
    currentCategoryIndex: MutableIntState
) {
    LazyRow(modifier = Modifier.padding(start = 16.dp)) {
        items(categories.size) { i ->
            val textColor = if (i == currentCategoryIndex.intValue) Color.White else primary
            val bgColor = if (i == currentCategoryIndex.intValue) primary else Color.White

            Box(modifier = Modifier.padding(end = 5.dp)) {
                Box(
                    modifier = Modifier
                        .clickable {
                            currentCategoryIndex.intValue = i
                        }
                        .background(color = bgColor, shape = RoundedCornerShape(5.dp))
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = categories[i],
                        style = TextStyle(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            lineHeight = 25.sp,
                            letterSpacing = 0.sp,
                            color = textColor,
                        ),
                    )
                }
            }
        }
    }
}