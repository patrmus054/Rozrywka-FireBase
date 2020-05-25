package com.example.rozrywka_firebase.data

data class Model (
    val id : Long = 0,
    val title: String = "",
    val date: String = "",
    val kind: ItemTypes = ItemTypes.MOVIE,
    val seen: Boolean = true
)
