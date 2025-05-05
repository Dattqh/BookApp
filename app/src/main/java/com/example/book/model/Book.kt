package com.example.book.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Chapter(
    val title: String,
    val content: String,
    val createdAt: String? = null
) : Parcelable

@Parcelize
data class Book(
    val _id: String? = null,
    val title: String,
    val author: String,
    val description: String,
    val genre: List<String> = emptyList(),
    val publishedYear: Int,
    val pages: Int,
    val coverImageUrl: String,
    val isFavorite: Boolean = false,
    val rating: Double = 0.0,
    val chapters: List<Chapter> = emptyList()
) : Parcelable

