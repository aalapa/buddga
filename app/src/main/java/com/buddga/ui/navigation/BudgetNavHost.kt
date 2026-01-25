package com.buddga.ui.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.buddga.ui.screens.accounts.AccountsScreen
import com.buddga.ui.screens.accounts.AddAccountScreen
import com.buddga.ui.screens.bills.AddBillScreen
import com.buddga.ui.screens.bills.UpcomingBillsScreen
import com.buddga.ui.screens.budgeting.AddBudgetScreen
import com.buddga.ui.screens.budgeting.BudgetingScreen
import com.buddga.ui.screens.cashflow.CashFlowScreen
import com.buddga.ui.screens.categories.AddCategoryScreen
import com.buddga.ui.screens.forecast.CashFlowForecastScreen
import com.buddga.ui.screens.transactions.AddTransactionScreen
import com.buddga.ui.screens.transactions.TransactionsScreen
import com.buddga.ui.screens.warning.CashFlowWarningScreen
import com.buddga.ui.screens.weekly.WeeklyCashFlowScreen

@Composable
fun BudgetNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Determine if we should show the bottom bar
    val showBottomBar = Screen.bottomNavItems.any { screen ->
        currentDestination?.hierarchy?.any { it.route == screen.route } == true
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    modifier = Modifier.height(56.dp)
                ) {
                    Screen.bottomNavItems.forEach { screen ->
                        val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (selected) screen.selectedIcon else screen.unselectedIcon,
                                    contentDescription = screen.title,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = null,
                            selected = selected,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Accounts.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Main bottom nav screens
            composable(Screen.Budgeting.route) {
                BudgetingScreen(
                    onNavigateToAddBudget = {
                        navController.navigate(Screen.AddBudget.route)
                    },
                    onNavigateToAddCategory = {
                        navController.navigate(Screen.AddCategory.route)
                    }
                )
            }

            composable(Screen.Accounts.route) {
                AccountsScreen(
                    onNavigateToAddAccount = {
                        navController.navigate(Screen.AddAccount.route)
                    }
                )
            }

            composable(Screen.Transactions.route) {
                TransactionsScreen(
                    onNavigateToAddTransaction = {
                        navController.navigate(Screen.AddTransaction.route)
                    }
                )
            }

            composable(Screen.CashFlow.route) {
                CashFlowScreen(
                    onNavigateToForecast = {
                        navController.navigate(Screen.CashFlowForecast.route)
                    },
                    onNavigateToBills = {
                        navController.navigate(Screen.UpcomingBills.route)
                    },
                    onNavigateToWeekly = {
                        navController.navigate(Screen.WeeklyCashFlow.route)
                    },
                    onNavigateToWarning = {
                        navController.navigate(Screen.CashFlowWarning.route)
                    }
                )
            }

            // Sub-screens
            composable(Screen.CashFlowForecast.route) {
                CashFlowForecastScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.UpcomingBills.route) {
                UpcomingBillsScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToAddBill = { navController.navigate(Screen.AddBill.route) }
                )
            }

            composable(Screen.WeeklyCashFlow.route) {
                WeeklyCashFlowScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.CashFlowWarning.route) {
                CashFlowWarningScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // Form screens
            composable(Screen.AddTransaction.route) {
                AddTransactionScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.AddAccount.route) {
                AddAccountScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.AddBudget.route) {
                AddBudgetScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.AddBill.route) {
                AddBillScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.AddCategory.route) {
                AddCategoryScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
