package com.buddga.ui.screens.accounts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buddga.domain.model.AccountType
import com.buddga.ui.components.AccountCard
import com.buddga.ui.theme.ExpenseRed
import com.buddga.ui.theme.IncomeGreen
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(
    onNavigateToAddAccount: () -> Unit,
    onNavigateToAccountDetail: (Long) -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: AccountsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val currencyFormat = remember { NumberFormat.getCurrencyInstance(Locale.US) }

    // Track expanded/collapsed state for each group (default: expanded)
    val expandedGroups = remember { mutableStateMapOf<String, Boolean>() }

    // Group accounts by budget status
    val budgetAccounts = uiState.accounts.filter { it.isOnBudget }
    val trackingAccounts = uiState.accounts.filter { !it.isOnBudget }

    // Calculate net worth breakdown
    val assetTypes = setOf(AccountType.CHECKING, AccountType.SAVINGS, AccountType.CASH, AccountType.INVESTMENT)
    val liabilityTypes = setOf(AccountType.CREDIT_CARD)

    val totalAssets = uiState.accounts
        .filter { it.type in assetTypes }
        .fold(BigDecimal.ZERO) { sum, account -> sum + account.balance }

    val totalLiabilities = uiState.accounts
        .filter { it.type in liabilityTypes }
        .fold(BigDecimal.ZERO) { sum, account -> sum + account.balance }
        .abs()

    val netWorth = totalAssets - totalLiabilities

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Accounts",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddAccount,
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Account"
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        if (uiState.accounts.isEmpty() && !uiState.isLoading) {
            // Empty state
            EmptyAccountsState(
                onAddAccount = onNavigateToAddAccount,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                // Net Worth Header Card
                item(key = "net_worth_header") {
                    NetWorthHeader(
                        netWorth = netWorth,
                        totalAssets = totalAssets,
                        totalLiabilities = totalLiabilities,
                        currencyFormat = currencyFormat,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                // Budget Accounts Section
                if (budgetAccounts.isNotEmpty()) {
                    val budgetTotal = budgetAccounts
                        .fold(BigDecimal.ZERO) { sum, account -> sum + account.balance }
                    val isBudgetExpanded = expandedGroups.getOrDefault("budget", true)

                    item(key = "budget_header") {
                        CollapsibleAccountGroupHeader(
                            title = "Budget Accounts",
                            totalBalance = budgetTotal,
                            accountCount = budgetAccounts.size,
                            isExpanded = isBudgetExpanded,
                            currencyFormat = currencyFormat,
                            onToggle = {
                                expandedGroups["budget"] = !isBudgetExpanded
                            },
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }

                    item(key = "budget_accounts") {
                        AnimatedVisibility(
                            visible = isBudgetExpanded,
                            enter = expandVertically(animationSpec = tween(300)) + fadeIn(animationSpec = tween(300)),
                            exit = shrinkVertically(animationSpec = tween(300)) + fadeOut(animationSpec = tween(200))
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                budgetAccounts.forEach { account ->
                                    AccountCard(
                                        account = account,
                                        onClick = { onNavigateToAccountDetail(account.id) }
                                    )
                                }
                            }
                        }
                    }
                }

                // Tracking Accounts Section
                if (trackingAccounts.isNotEmpty()) {
                    val trackingTotal = trackingAccounts
                        .fold(BigDecimal.ZERO) { sum, account -> sum + account.balance }
                    val isTrackingExpanded = expandedGroups.getOrDefault("tracking", true)

                    item(key = "tracking_header") {
                        Spacer(modifier = Modifier.height(8.dp))
                        CollapsibleAccountGroupHeader(
                            title = "Tracking Accounts",
                            totalBalance = trackingTotal,
                            accountCount = trackingAccounts.size,
                            isExpanded = isTrackingExpanded,
                            currencyFormat = currencyFormat,
                            onToggle = {
                                expandedGroups["tracking"] = !isTrackingExpanded
                            },
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }

                    item(key = "tracking_accounts") {
                        AnimatedVisibility(
                            visible = isTrackingExpanded,
                            enter = expandVertically(animationSpec = tween(300)) + fadeIn(animationSpec = tween(300)),
                            exit = shrinkVertically(animationSpec = tween(300)) + fadeOut(animationSpec = tween(200))
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                trackingAccounts.forEach { account ->
                                    AccountCard(
                                        account = account,
                                        onClick = { onNavigateToAccountDetail(account.id) }
                                    )
                                }
                            }
                        }
                    }
                }

                // Bottom spacing for FAB clearance
                item(key = "bottom_spacer") {
                    Spacer(modifier = Modifier.height(88.dp))
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Net Worth Header
// ---------------------------------------------------------------------------

@Composable
private fun NetWorthHeader(
    netWorth: BigDecimal,
    totalAssets: BigDecimal,
    totalLiabilities: BigDecimal,
    currencyFormat: NumberFormat,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Net Worth",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = currencyFormat.format(netWorth),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.height(20.dp))

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f),
                    thickness = 1.dp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Assets column
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Assets",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = currencyFormat.format(totalAssets),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = IncomeGreen
                        )
                    }

                    // Liabilities column
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Liabilities",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "-${currencyFormat.format(totalLiabilities)}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = ExpenseRed
                        )
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Collapsible Account Group Header
// ---------------------------------------------------------------------------

@Composable
private fun CollapsibleAccountGroupHeader(
    title: String,
    totalBalance: BigDecimal,
    accountCount: Int,
    isExpanded: Boolean,
    currencyFormat: NumberFormat,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val arrowRotation by animateFloatAsState(
        targetValue = if (isExpanded) 0f else -90f,
        animationSpec = tween(durationMillis = 300),
        label = "arrow_rotation"
    )

    val isNegative = totalBalance < BigDecimal.ZERO

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onToggle() }
            .padding(vertical = 12.dp, horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .size(20.dp)
                    .rotate(arrowRotation)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$accountCount",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }

        Text(
            text = currencyFormat.format(totalBalance),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = if (isNegative) ExpenseRed else MaterialTheme.colorScheme.onSurface
        )
    }
}

// ---------------------------------------------------------------------------
// Empty State
// ---------------------------------------------------------------------------

@Composable
private fun EmptyAccountsState(
    onAddAccount: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountBalanceWallet,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Welcome to Buddga",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Add your first account to start taking\ncontrol of your finances.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            onClick = onAddAccount,
            modifier = Modifier.fillMaxWidth(0.6f),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Add Account",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}
