package com.example.tolist_final.data_class

data class Reminder(
    val title: String,
    val notes: String,
    val date: String,
    val time: String,
    val categoryId: String // Foreign key to categories
)

data class Category(
    val id: String? = null,
    val name: String,
    val description: String
)

data class Label(
    val id: String? = null,
    val name: String? = null
)

