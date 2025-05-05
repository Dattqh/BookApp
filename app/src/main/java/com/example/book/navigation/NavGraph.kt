package com.example.book.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.book.screens.*

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("favorite") {
            FavoriteScreen(navController)
        }
        composable("profile") {
            ProfileScreen(navController)
        }
        composable("reading") {
            ReadingScreen(navController)
        }
        composable("book_detail/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
            BookDetailScreen(bookId = bookId)
        }
    }
}