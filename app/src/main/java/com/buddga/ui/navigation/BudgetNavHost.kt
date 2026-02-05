package com.buddga.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.buddga.ui.screens.accounts.AccountDetailScreen
import com.buddga.ui.screens.accounts.AccountsScreen
import com.buddga.ui.screens.accounts.AddAccountScreen
import com.buddga.ui.screens.bills.AddBillScreen
import com.buddga.ui.screens.bills.UpcomingBillsScreen
import com.buddga.ui.screens.budgeting.AddBudgetScreen
import com.buddga.ui.screens.budgeting.BudgetingScreen
import com.buddga.ui.screens.cashflow.CashFlowScreen
import com.buddga.ui.screens.categories.AddCategoryGroupScreen
import com.buddga.ui.screens.categories.AddCategoryScreen
import com.buddga.ui.screens.forecast.CashFlowForecastScreen
import com.buddga.ui.screens.reports.ReportsScreen
import com.buddga.ui.screens.settings.SettingsScreen
import com.buddga.ui.screens.transactions.AddTransactionScreen
import com.buddga.ui.screens.transactions.TransactionsScreen
import com.buddga.ui.screens.warning.CashFlowWarningScreen
import com.buddga.ui.screens.weekly.WeeklyCashFlowScreen

@Composable
fun BudgetNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var newGroupName by remember { mutableStateOf<String?>(null) }

    val showBottomBar = Screen.bottomNavItems.any { screen ->
        currentDestination?.hierarchy?.any { it.route == screen.route } == true
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 2.dp
                ) {
                    Screen.bottomNavItems.forEach { screen ->
                        val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (selected) screen.selectedIcon else screen.unselectedIcon,
                                    contentDescription = screen.title,
                                    modifier = Modifier.size(22.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = screen.title,
                                    fontSize = 10.sp,
                                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                                    maxLines = 1
                                )
                            },
                            selected = selected,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Budgeting.route,
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
                    },
                    onNavigateToAccountDetail = { accountId ->
                        navController.navigate(Screen.AccountDetail.createRoute(accountId))
                    },
                    onNavigateToSettings = {
                        navController.navigate(Screen.Settings.route)
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

            composable(Screen.Reports.route) {
                ReportsScreen()
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

            composable(
                route = Screen.AccountDetail.route,
                arguments = listOf(navArgument("accountId") { type = NavType.LongType })
            ) { backStackEntry ->
                val accountId = backStackEntry.arguments?.getLong("accountId") ?: 0L
                AccountDetailScreen(
                    accountId = accountId,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToAddTransaction = {
                        navController.navigate(Screen.AddTransaction.route)
                    }
                )
            }

            composable(Screen.AddBudget.route) {
                AddBudgetScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToAddCategory = {
                        navController.navigate(Screen.AddCategory.route)
                    }
                )
            }

            composable(Screen.AddBill.route) {
                AddBillScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.AddCategory.route) {
                AddCategoryScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToCreateGroup = {
                        navController.navigate(Screen.AddCategoryGroup.route)
                    },
                    newGroupName = newGroupName
                )
            }

            composable(Screen.AddCategoryGroup.route) {
                AddCategoryGroupScreen(
                    onNavigateBack = { groupName ->
                        if (groupName != null) {
                            newGroupName = groupName
                        }
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.Settings.route) {
                SettingsScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
