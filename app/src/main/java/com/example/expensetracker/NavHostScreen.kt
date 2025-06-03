package com.example.expensetracker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.features.expense.AddExpenseScreen
import com.example.expensetracker.features.home.HomeScreen
import com.example.expensetracker.features.stats.StatsScreen
import com.example.expensetracker.features.transaction.TransactionScreen
import com.example.expensetracker.ui.theme.gray
import com.example.expensetracker.ui.theme.zinc

@Composable
fun NavHostScreen() {

    val navController = rememberNavController()
    val isBottomBarVisible = remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(isBottomBarVisible.value) {

                NavigationBottomBar(
                    navController = navController,
                    items = listOf(
                        NavItem("Home", R.drawable.ic_home, "/home"),
                        NavItem("Stats", R.drawable.bar_chart, "/stats"),
                        NavItem("Transaction", R.drawable.wallet, "/transaction")

                    )
                )
            }
        }
    ) {
        NavHost(
            navController = navController, startDestination = "/home",
            modifier = Modifier.padding(it)
        ) {
            composable("/home") {
                isBottomBarVisible.value = true
                HomeScreen(navController)
            }
            composable("/addExpense") {
                isBottomBarVisible.value = false
                AddExpenseScreen(navController)
            }
            composable("/stats") {
                isBottomBarVisible.value = true
                StatsScreen(navController)
            }
            composable("/transaction") {
                isBottomBarVisible.value = true
                TransactionScreen(navController)
            }
        }
    }


}

data class NavItem(val title: String, val icon: Int, val route: String)

@Composable
fun NavigationBottomBar(
    navController: NavController,
    items: List<NavItem>
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar {
        items.forEach {
            val isSelected = currentRoute == it.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute != it.route) {
                        navController.navigate(it.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    }
                },
                icon = {
                    Image(
                        painter = painterResource(id = it.icon),
                        contentDescription = it.title,
                        colorFilter = ColorFilter.tint(
                            if (isSelected) zinc else gray
                        )
                    )
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = zinc,
                    selectedTextColor = zinc,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}
