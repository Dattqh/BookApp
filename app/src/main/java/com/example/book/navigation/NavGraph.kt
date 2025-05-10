package com.example.book.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.book.screens.*
import com.example.book.viewmodel.LoginViewModel
import com.example.book.viewmodel.RegistrationViewModel
import org.koin.androidx.compose.koinViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("registration")
    object Main : Screen("main")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object Reading : Screen("reading")
    object BookDetail : Screen("book_detail/{bookId}") {
        fun createRoute(bookId: String) = "book_detail/$bookId"
    }
}

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable(Screen.Splash.route) {
            SplashScreen(onGetStartedClick = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Login.route) {
            val viewModel: LoginViewModel = koinViewModel()
            LoginScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(Screen.Register.route) {
            val viewModel: RegistrationViewModel = koinViewModel()
            RegistrationScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(Screen.Favorite.route) {
            FavoriteScreen(navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }

        composable(Screen.Reading.route) {
            ReadingScreen(navController)
        }

        composable(Screen.BookDetail.route) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
            BookDetailScreen(bookId = bookId)
        }

        composable("home") {
            HomeScreen()
        }
    }
}
