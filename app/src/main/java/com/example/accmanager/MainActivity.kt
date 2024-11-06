package com.example.accmanager

import LoginScreen
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.accmanager.screens.AccountDetailScreen
import com.example.accmanager.screens.AccountScreen
import com.example.accmanager.screens.BottomNavMenu
import com.example.accmanager.screens.ReportScreen
import com.example.accmanager.screens.SettingScreen
import com.example.accmanager.ui.theme.AccManagerTheme

sealed class DestinationScreens(val route: String) {
    object Login:DestinationScreens("login")
    object Register:DestinationScreens("register")
    object Account : DestinationScreens("account")
    object Setting : DestinationScreens("setting")
    object Report : DestinationScreens("report")
    object AccountDetailScreen : DestinationScreens("accountDetail/{accountName}"){
        fun createRoute(id: String) = "accountDetail/$id"
    }
}

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AccManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatAppNavigation()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    fun ChatAppNavigation() {
        val navController = rememberNavController()
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        val selectedItem = when (currentRoute) {
            DestinationScreens.Account.route -> BottomNavMenu.ACCOUNTS
            DestinationScreens.Report.route -> BottomNavMenu.REPORT
            DestinationScreens.Setting.route -> BottomNavMenu.SETTINGS
            else -> BottomNavMenu.ACCOUNTS
        }
        Scaffold(bottomBar = {
            BottomNavMenu(selectedItem = selectedItem, navController = navController)
        }) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = DestinationScreens.Account.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(DestinationScreens.Account.route) {
                    AccountScreen(navController)
                }
                composable(DestinationScreens.Setting.route) {
                    SettingScreen(navController)
                }
                composable(DestinationScreens.Report.route) {
                    ReportScreen(navController)
                }
                composable(DestinationScreens.Login.route) {
                   // LoginScreen(navController=navController)
                }

                composable(DestinationScreens.AccountDetailScreen.route) {
                    val accountName = it.arguments?.getString("accountName")
                    accountName.let {
                        if (accountName != null) {
                            AccountDetailScreen(accountName=accountName,
                                navController = navController)
                        }
                    }

                }

            }
        }
    }


}
