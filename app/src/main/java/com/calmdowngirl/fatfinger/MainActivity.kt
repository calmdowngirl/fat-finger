package com.calmdowngirl.fatfinger

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.calmdowngirl.fatfinger.ui.theme.FatFingerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            val onBackPressedCallback = remember {
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        // Code to execute when the back button is pressed
                        Log.d("ffgr", "Back btn clicked")
                    }
                }
            }
            onBackPressedDispatcher.addCallback(onBackPressedCallback)

            FatFingerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = AppRoutes.Home.route,
                    ) {
                        composable(
                            route = AppRoutes.Home.route,
                            arguments = listOf(
                                navArgument("component") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            val component =
                                backStackEntry.arguments?.getString("component") ?: HomeComponent.Info.id
                            HomeScreen(
                                navController = navController,
                                component = component
                            )
                        }

                        composable(AppRoutes.Support.route) {
                            SupportMe(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

sealed class AppRoutes(val route: String) {
    object Home : AppRoutes("home/{component}")
    object Support : AppRoutes("support")
}

sealed class HomeComponent(val id: String) {
    object CanvasSettings : HomeComponent("canvas-settings")
    object Palette : HomeComponent("palette")
    object Info : HomeComponent("info")
}
