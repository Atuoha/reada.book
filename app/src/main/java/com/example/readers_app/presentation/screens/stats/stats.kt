package com.example.readers_app.presentation.screens.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.readers_app.components.BookRead
import com.example.readers_app.domain.models.books
import com.example.readers_app.presentation.screens.stats.widgets.StatBox

@Composable
fun StatsScreen(navController: NavController){
   Column(modifier = Modifier
       .padding(horizontal = 16.dp, vertical = 20.dp)
       .fillMaxSize()) {
       Spacer(modifier = Modifier.height(10.dp))
       StatBox()
       Spacer(modifier = Modifier.height(15.dp))
           LazyColumn {
               items(books.count()) { index ->
                   val book = books[index]

                   BookRead(index,book)
               }
           }
   }
}
