package com.example.gyantra.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.gyantra.presentation.AllBooksByCategory.BooksByCategoryScreen
import com.example.gyantra.presentation.HomeScreen.HomeScreen
import com.example.gyantra.presentation.PdfViewerScreen

@Composable
fun NavGraph(navController: NavController) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HomeScreen) {

        composable<Routes.HomeScreen>{
            HomeScreen(navController = navController)
        }

        composable<Routes.BooksByCategory> { backstackEntry ->
            val data : Routes.BooksByCategory = backstackEntry.toRoute()
            BooksByCategoryScreen(category = data.category, navController = navController)
        }

        composable<Routes.ShowPdfScreen> { backStackEntry ->
            val data : Routes.ShowPdfScreen = backStackEntry.toRoute()
            PdfViewerScreen(url = data.url)
        }


    }



}