package com.example.readers_app.domain.models

import com.example.readers_app.R

data class Book(val title: String, val author: String, val image: Int) {
}


val books = listOf(
    Book("A Court of Thrones and Roses", "Sarah Maas", R.drawable.b1),
    Book("Everything I know about Parties", "Dolly Alderton", R.drawable.b2),
    Book("Funny Story", "Emily Henry", R.drawable.b3),
    Book("The Heaven & Earth Grocery Store", "James Mcbride", R.drawable.b4),
    Book("The Midnight Library", "Matt Haig", R.drawable.b5),
    Book("The Hundred Years of War in Palestine", "Rhasid Khalidi", R.drawable.b6),
    Book("A Little Life", "Hanya Yanagihara", R.drawable.b7),
    Book("This is How You Lose The Time War", "Max Gladstone", R.drawable.b8),
    Book("A Court of Mist and Fury", "Sarah J Maas", R.drawable.b9),
    Book("The Lioness of Boston", "Emily Franklin", R.drawable.b10),
)