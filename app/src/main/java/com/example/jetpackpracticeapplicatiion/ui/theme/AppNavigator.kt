package com.example.jetpackpracticeapplicatiion.ui.theme

// AppNavigator.kt
import ProductListScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackpracticeapplicatiion.ui.theme.screens.ProductDetailScreen
import com.example.jetpackpracticeapplicatiion.ui.theme.screens.StudentFormWithValidations

//import com.example.jetpackpracticeapplicatiion.ui.theme.screens.ProductDetailScreen

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "student_form") {
        composable("student_form") {
            StudentFormWithValidations(navController = navController)
        }
        composable("product_list") {
            ProductListScreen(navController = navController)
        }
        composable("product_detail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toInt()
            ProductDetailScreen(productId = productId,navController = navController)
        }
    }
}
