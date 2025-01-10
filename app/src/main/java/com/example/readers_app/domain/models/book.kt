package com.example.readers_app.domain.models

import com.example.readers_app.R


data class Book(
    val id: String,
    val title: String,
    val author: String,
    val image: Int,
    val description: String,
    val pageCount: Int,
    val previewLink: String
)

val books = listOf(
    Book(
        id = "1",
        title = "A Court of Thrones and Roses",
        author = "Sarah Maas",
        image = R.drawable.b1,
        description = "A young huntress is dragged into a dangerous and magical world after unknowingly crossing into faerie lands. Filled with twists, romance, and deadly intrigue, this novel weaves a tale of survival and love amidst a dark and enchanting realm.",
        pageCount = 432,
        previewLink = "https://example.com/a-court-of-thrones-and-roses"
    ),
    Book(
        id = "2",
        title = "Everything I Know About Parties",
        author = "Dolly Alderton",
        image = R.drawable.b2,
        description = "In this humorous and heartfelt memoir, Dolly Alderton explores the highs and lows of modern life, from wild parties and love stories to personal growth and the unbreakable bonds of friendship. A witty exploration of adulthood and self-discovery.",
        pageCount = 256,
        previewLink = "https://example.com/everything-i-know-about-parties"
    ),
    Book(
        id = "3",
        title = "Funny Story",
        author = "Emily Henry",
        image = R.drawable.b3,
        description = "A laugh-out-loud romantic comedy about two opposites who find themselves entangled in an unexpected journey of love, misunderstandings, and personal growth. This charming story will leave you smiling and rooting for the characters.",
        pageCount = 320,
        previewLink = "https://example.com/funny-story"
    ),
    Book(
        id = "4",
        title = "The Heaven & Earth Grocery Store",
        author = "James McBride",
        image = R.drawable.b4,
        description = "Set in a tight-knit community, this novel tells the moving story of a humble grocery store and its role in the lives of the locals. Themes of resilience, love, and the enduring power of community are beautifully woven together in this unforgettable tale.",
        pageCount = 400,
        previewLink = "https://example.com/heaven-earth-grocery-store"
    ),
    Book(
        id = "5",
        title = "The Midnight Library",
        author = "Matt Haig",
        image = R.drawable.b5,
        description = "A captivating exploration of life’s infinite possibilities, this story follows Nora as she discovers a library that allows her to explore alternate versions of her life. With hope and wisdom, it reminds readers of the beauty of every choice we make.",
        pageCount = 304,
        previewLink = "https://example.com/the-midnight-library"
    ),
    Book(
        id = "6",
        title = "The Hundred Years of War in Palestine",
        author = "Rhasid Khalidi",
        image = R.drawable.b6,
        description = "An insightful and meticulously researched account of the century-long conflict in Palestine, this book offers a comprehensive analysis of the historical, political, and cultural forces that have shaped the region and its people.",
        pageCount = 352,
        previewLink = "https://example.com/hundred-years-war-palestine"
    ),
    Book(
        id = "7",
        title = "A Little Life",
        author = "Hanya Yanagihara",
        image = R.drawable.b7,
        description = "A profoundly emotional story of friendship and survival, this novel follows four college friends as they navigate their lives in New York City. Exploring themes of trauma, love, and resilience, it’s a story that will linger with readers long after the last page.",
        pageCount = 720,
        previewLink = "https://example.com/a-little-life"
    ),
    Book(
        id = "8",
        title = "This is How You Lose The Time War",
        author = "Max Gladstone",
        image = R.drawable.b8,
        description = "A breathtaking tale of love and rivalry across the vast tapestry of time. Two agents on opposite sides of a time-traveling war find themselves writing letters that blur the line between friend and foe, leading to a story as thrilling as it is heartfelt.",
        pageCount = 208,
        previewLink = "https://example.com/lose-the-time-war"
    ),
    Book(
        id = "9",
        title = "A Court of Mist and Fury",
        author = "Sarah J Maas",
        image = R.drawable.b9,
        description = "The breathtaking sequel to A Court of Thrones and Roses, this book takes readers deeper into a world of magic, war, and romance. Feyre must navigate shifting alliances and rediscover her own strength to protect those she loves.",
        pageCount = 624,
        previewLink = "https://example.com/a-court-of-mist-and-fury"
    ),
    Book(
        id = "10",
        title = "The Lioness of Boston",
        author = "Emily Franklin",
        image = R.drawable.b10,
        description = "The inspiring tale of Isabella Stewart Gardner, a bold and determined woman who defied conventions and built one of the most renowned museums in Boston. A story of passion, perseverance, and an unyielding vision for the arts.",
        pageCount = 360,
        previewLink = "https://example.com/the-lioness-of-boston"
    )
)
