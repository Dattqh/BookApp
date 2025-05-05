package com.example.book.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun FavoriteScreen(navController: NavController) {
    LazyColumn {
        item {
            Text(text = "Danh sách sách yêu thích")
            // Thêm danh sách sách yêu thích
        }
    }
}