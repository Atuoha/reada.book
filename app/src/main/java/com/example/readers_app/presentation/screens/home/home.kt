package com.example.readers_app.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.readers_app.components.SectionWithAll
import com.example.readers_app.components.SingleBook
import com.example.readers_app.core.enums.Screens
import com.example.readers_app.domain.models.books
import com.example.readers_app.presentation.screens.home.widgets.CarouselComponent
import com.example.readers_app.presentation.screens.home.widgets.CategorySection
import com.example.readers_app.presentation.screens.home.widgets.CurrentlyReading
import com.example.readers_app.presentation.screens.home.widgets.GetStarted
import com.example.readers_app.widgets.TopSection



@Composable
fun HomeScreen(navController: NavController) {
    val currentCategoryIndex = remember { mutableIntStateOf(0) }
    val categories = listOf("Fiction", "Non Fiction", "Health", "Technology", "History", "Science")


    Column(
        modifier = Modifier
            .padding(vertical = 20.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            TopSection()
            Spacer(modifier = Modifier.height(10.dp))
            GetStarted()
            Spacer(modifier = Modifier.height(10.dp))
            SectionWithAll(title = "Currently Reading") {
                navController.navigate(Screens.CurrentlyReading.name)
            }
            Spacer(modifier = Modifier.height(5.dp))
            CurrentlyReading()
            Spacer(modifier = Modifier.height(10.dp))
        }
        CarouselComponent()
        Spacer(modifier = Modifier.height(10.dp))
        CategorySection(categories, currentCategoryIndex)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(books.count()) { index ->
                val book = books[index]
                SingleBook(book = book){
                    navController.navigate("${Screens.Details.name}/${book.id}")
                }
            }
        }

    }


}








